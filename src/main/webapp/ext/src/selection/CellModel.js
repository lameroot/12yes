/**
 *
 */
Ext.define('Ext.selection.CellModel', {
    extend: 'Ext.selection.Model',
    alias: 'selection.cellmodel',
    requires: ['Ext.util.KeyNav'],

    isCellModel: true,

    /**
     * @cfg {Boolean} enableKeyNav
     * Turns on/off keyboard navigation within the grid.
     */
    enableKeyNav: true,

    /**
     * @cfg {Boolean} preventWrap
     * Set this configuration to true to prevent wrapping around of selection as
     * a user navigates to the first or last column.
     */
    preventWrap: false,

    // private property to use when firing a deselect when no old selection exists.
    noSelection: {
        row: -1,
        column: -1
    },

    constructor: function () {
        this.addEvents(
            /**
             * @event deselect
             * Fired after a cell is deselected
             * @param {Ext.selection.CellModel} this
             * @param {Ext.data.Model} record The record of the deselected cell
             * @param {Number} row The row index deselected
             * @param {Number} column The column index deselected
             */
            'deselect',

            /**
             * @event select
             * Fired after a cell is selected
             * @param {Ext.selection.CellModel} this
             * @param {Ext.data.Model} record The record of the selected cell
             * @param {Number} row The row index selected
             * @param {Number} column The column index selected
             */
            'select'
        );
        this.callParent(arguments);
    },

    bindComponent: function (view) {
        var me = this,
            grid = view.ownerCt;
        me.primaryView = view;
        me.views = me.views || [];
        me.views.push(view);
        me.bindStore(view.getStore(), true);

        view.on({
            cellmousedown: me.onMouseDown,
            refresh: me.onViewRefresh,
            scope: me
        });
        if (grid.optimizedColumnMove !== false) {
            grid.on('columnmove', me.onColumnMove, me);
        }

        if (me.enableKeyNav) {
            me.initKeyNav(view);
        }
    },

    initKeyNav: function (view) {
        var me = this;

        if (!view.rendered) {
            view.on('render', Ext.Function.bind(me.initKeyNav, me, [view], 0), me, {single: true});
            return;
        }

        view.el.set({
            tabIndex: -1
        });

        // view.el has tabIndex -1 to allow for
        // keyboard events to be passed to it.
        me.keyNav = new Ext.util.KeyNav({
            target: view.el,
            ignoreInputFields: true,
            up: me.onKeyUp,
            down: me.onKeyDown,
            right: me.onKeyRight,
            left: me.onKeyLeft,
            tab: me.onKeyTab,
            scope: me
        });
    },

    getHeaderCt: function () {
        var selection = this.getCurrentPosition(),
            view = selection ? selection.view : this.primaryView;

        return view.headerCt;
    },

    onKeyUp: function (e, t) {
        this.keyNavigation = true;
        this.move('up', e);
        this.keyNavigation = false;
    },

    onKeyDown: function (e, t) {
        this.keyNavigation = true;
        this.move('down', e);
        this.keyNavigation = false;
    },

    onKeyLeft: function (e, t) {
        this.keyNavigation = true;
        this.move('left', e);
        this.keyNavigation = false;
    },

    onKeyRight: function (e, t) {
        this.keyNavigation = true;
        this.move('right', e);
        this.keyNavigation = false;
    },

    move: function (dir, e) {
        var me = this,
            pos = me.getCurrentPosition(),

        // Calculate the new row and column position
            newPos = pos.view.walkCells(pos, dir, e, me.preventWrap);

        // If walk was successful, select new Position
        if (newPos) {
            newPos.view = pos.view;
            return me.setCurrentPosition(newPos);
        }
        // <debug>
        // Enforce code correctness in unbuilt source.
        return null;
        // </debug>
    },

    /**
     * Returns the current position in the format {row: row, column: column}
     */
    getCurrentPosition: function () {
        return this.selection;
    },

    /**
     * Sets the current position
     * @param {Object} position The position to set.
     */
    setCurrentPosition: function (pos) {
        var me = this;

        // onSelectChange uses lastSelection and nextSelection
        me.lastSelection = me.selection;
        if (me.selection) {
            me.onCellDeselect(me.selection);
        }

        if (pos) {
            me.nextSelection = new me.Selection(me);
            me.nextSelection.setPosition(pos);
            me.onCellSelect(me.nextSelection);

            // Deselect triggered by new selection will kill the selection property, so restore it here.
            return me.selection = me.nextSelection;
        }
        // <debug>
        // Enforce code correctness in unbuilt source.
        return null;
        // </debug>
    },

    // Keep selection model in consistent state upon record deletion.
    onStoreRemove: function (store, record, index) {
        var me = this,
            pos = me.getCurrentPosition();

        me.callParent(arguments);
        if (pos) {
            // Deleting the row containing the selection.
            // Attempt to reselect the same cell which has moved up if there is one
            if (pos.row == index) {
                if (index < store.getCount() - 1) {
                    pos.setPosition(index, pos.column);
                    me.setCurrentPosition(pos);
                } else {
                    delete me.selection;
                }
            }
            // Deleting a row before the selection.
            // Move the selection up by one row
            else if (index < pos.row) {
                pos.setPosition(pos.row - 1, pos.column);
                me.setCurrentPosition(pos);
            }
        }
    },

    /**
     * Set the current position based on where the user clicks.
     * @private
     */
    onMouseDown: function (view, cell, cellIndex, record, row, rowIndex, e) {
        this.setCurrentPosition({
            view: view,
            row: rowIndex,
            column: cellIndex
        });
    },

    // notify the view that the cell has been selected to update the ui
    // appropriately and bring the cell into focus
    onCellSelect: function (position, supressEvent) {
        if (position && position.row !== undefined && position.row > -1) {
            this.doSelect(position.view.getStore().getAt(position.row), /*keepExisting*/false, supressEvent);
        }
    },

    // notify view that the cell has been deselected to update the ui
    // appropriately
    onCellDeselect: function (position, supressEvent) {
        if (position && position.row !== undefined) {
            this.doDeselect(position.view.getStore().getAt(position.row), supressEvent);
        }
    },

    onSelectChange: function (record, isSelected, suppressEvent, commitFn) {
        var me = this,
            pos,
            eventName,
            view;

        if (isSelected) {
            pos = me.nextSelection;
            eventName = 'select';
        } else {
            pos = me.lastSelection || me.noSelection;
            eventName = 'deselect';
        }

        // CellModel may be shared between two sides of a Lockable.
        // The position must include a reference to the view in which the selection is current.
        // Ensure we use the view specifiied by the position.
        view = pos.view || me.primaryView;

        if ((suppressEvent || me.fireEvent('before' + eventName, me, record, pos.row, pos.column)) !== false &&
            commitFn() !== false) {

            if (isSelected) {
                view.onCellSelect(pos);
                view.onCellFocus(pos);
            } else {
                view.onCellDeselect(pos);
                delete me.selection;
            }

            if (!suppressEvent) {
                me.fireEvent(eventName, me, record, pos.row, pos.column);
            }
        }
    },

    // Tab key from the View's KeyNav, *not* from an editor.
    onKeyTab: function (e, t) {
        var me = this,
            editingPlugin = me.getCurrentPosition().view.editingPlugin;

        // If we were in editing mode, but just focused on a non-editable cell, behave as if we tabbed off an editable field
        if (editingPlugin && me.wasEditing) {
            me.onEditorTab(editingPlugin, e)
        } else {
            me.move(e.shiftKey ? 'left' : 'right', e);
        }
    },

    onEditorTab: function (editingPlugin, e) {
        var me = this,
            direction = e.shiftKey ? 'left' : 'right',
            position = me.move(direction, e);

        // Navigation had somewhere to go.... not hit the buffers.
        if (position) {
            // If we were able to begin editing clear the wasEditing flag. It gets set during navigation off an active edit.
            if (editingPlugin.startEditByPosition(position)) {
                me.wasEditing = false;
            }
            // If we could not continue editing...
            // Set a flag that we should go back into editing mode upon next onKeyTab call
            else {
                me.wasEditing = true;
                if (!position.columnHeader.dataIndex) {
                    me.onEditorTab(editingPlugin, e);
                }
            }
        }
    },

    refresh: function () {
        var pos = this.getCurrentPosition(),
            selRowIdx;

        // Synchronize the current position's row with the row of the last selected record.
        if (pos && (selRowIdx = this.store.indexOf(this.selected.last())) !== -1) {
            pos.row = selRowIdx;
        }
    },

    /**
     * @private
     * When grid uses {@link Ext.panel.Table#optimizedColumnMove optimizedColumnMove} (the default), this is added as a
     * {@link Ext.panel.Table#columnmove columnmove} handler to correctly maintain the
     * selected column using the same column header.
     *
     * If optimizedColumnMove === false, (which some grid Features set) then the view is refreshed,
     * so this is not added as a handler because the selected column.
     */
    onColumnMove: function (headerCt, header, fromIdx, toIdx) {
        var grid = headerCt.up('tablepanel');
        if (grid) {
            this.onViewRefresh(grid.view);
        }
    },

    onViewRefresh: function (view) {
        var me = this,
            pos = me.getCurrentPosition(),
            headerCt = view.headerCt,
            record, columnHeader;

        // Re-establish selection of the same cell coordinate.
        // DO NOT fire events because the selected 
        if (pos && pos.view === view) {
            record = pos.record;
            columnHeader = pos.columnHeader;

            // After a refresh, recreate the selection using the same record and grid column as before
            if (!columnHeader.isDescendantOf(headerCt)) {
                // column header is not a child of the header container
                // this happens when the grid is reconfigured with new columns
                // make a best effor to select something by matching on id, then text, then dataIndex
                columnHeader = headerCt.queryById(columnHeader.id) ||
                    headerCt.down('[text="' + columnHeader.text + '"]') ||
                    headerCt.down('[dataIndex="' + columnHeader.dataIndex + '"]');
            }

            // If we have a columnHeader (either the column header that already exists in
            // the headerCt, or a suitable match that was found after reconfiguration)
            // AND the record still exists in the store (or a record matching the id of
            // the previously selected record) We are ok to go ahead and set the selection
            if (columnHeader && (view.store.indexOfId(record.getId()) !== -1)) {
                me.setCurrentPosition({
                    row: record,
                    column: columnHeader,
                    view: view
                });
            }
        }
    },

    selectByPosition: function (position) {
        this.setCurrentPosition(position);
    }
}, function () {

    // Encapsulate a single selection position.
    // Maintains { row: n, column: n, record: r, columnHeader: c}
    var Selection = this.prototype.Selection = function (model) {
        this.model = model;
    };
    // Selection row/record & column/columnHeader
    Selection.prototype.setPosition = function (row, col) {
        var me = this,
            view;

        // We were passed {row: 1, column: 2, view: myView}
        if (arguments.length === 1) {

            // SelectionModel is shared between both sides of a locking grid.
            // It can be positioned on either view.
            if (row.view) {
                me.view = view = row.view;
            }
            col = row.column;
            row = row.row;
        }

        // If setting the position without specifying a view, and the position is already without a view
        // use the owning Model's primary view
        if (!view) {
            me.view = view = me.model.primaryView;
        }

        // Row index passed
        if (typeof row === 'number') {
            me.row = row;
            me.record = view.store.getAt(row);
        }
        // row is a Record
        else if (row.isModel) {
            me.record = row;
            me.row = view.indexOf(row);
        }
        // row is a grid row
        else if (row.tagName) {
            me.record = view.getRecord(row);
            me.row = view.indexOf(me.record);
        }

        // column index passed
        if (typeof col === 'number') {
            me.column = col;
            me.columnHeader = view.getHeaderAtIndex(col);
        }
        // col is a column Header
        else {
            me.columnHeader = col;
            me.column = col.getIndex();
        }
        return me;
    }
});