/**
 * A mechanism for displaying data using custom layout templates and formatting.
 *
 * The View uses an {@link Ext.XTemplate} as its internal templating mechanism, and is bound to an
 * {@link Ext.data.Store} so that as the data in the store changes the view is automatically updated
 * to reflect the changes. The view also provides built-in behavior for many common events that can
 * occur for its contained items including click, doubleclick, mouseover, mouseout, etc. as well as a
 * built-in selection model. **In order to use these features, an {@link #itemSelector} config must
 * be provided for the View to determine what nodes it will be working with.**
 *
 * The example below binds a View to a {@link Ext.data.Store} and renders it into an {@link Ext.panel.Panel}.
 *
 *     @example
 *     Ext.define('Image', {
 *         extend: 'Ext.data.Model',
 *         fields: [
 *             { name:'src', type:'string' },
 *             { name:'caption', type:'string' }
 *         ]
 *     });
 *
 *     Ext.create('Ext.data.Store', {
 *         id:'imagesStore',
 *         model: 'Image',
 *         data: [
 *             { src:'http://www.sencha.com/img/20110215-feat-drawing.png', caption:'Drawing & Charts' },
 *             { src:'http://www.sencha.com/img/20110215-feat-data.png', caption:'Advanced Data' },
 *             { src:'http://www.sencha.com/img/20110215-feat-html5.png', caption:'Overhauled Theme' },
 *             { src:'http://www.sencha.com/img/20110215-feat-perf.png', caption:'Performance Tuned' }
 *         ]
 *     });
 *
 *     var imageTpl = new Ext.XTemplate(
 *         '<tpl for=".">',
 *             '<div style="margin-bottom: 10px;" class="thumb-wrap">',
 *               '<img src="{src}" />',
 *               '<br/><span>{caption}</span>',
 *             '</div>',
 *         '</tpl>'
 *     );
 *
 *     Ext.create('Ext.view.View', {
 *         store: Ext.data.StoreManager.lookup('imagesStore'),
 *         tpl: imageTpl,
 *         itemSelector: 'div.thumb-wrap',
 *         emptyText: 'No images available',
 *         renderTo: Ext.getBody()
 *     });
 */
Ext.define('Ext.view.View', {
    extend: 'Ext.view.AbstractView',
    alternateClassName: 'Ext.DataView',
    alias: 'widget.dataview',

    deferHighlight: (Ext.isIE6 || Ext.isIE7) ? 100 : 0,

    inputTagRe: /^textarea$|^input$/i,

    inheritableStatics: {
        EventMap: {
            mousedown: 'MouseDown',
            mouseup: 'MouseUp',
            click: 'Click',
            dblclick: 'DblClick',
            contextmenu: 'ContextMenu',
            mouseover: 'MouseOver',
            mouseout: 'MouseOut',
            mouseenter: 'MouseEnter',
            mouseleave: 'MouseLeave',
            keydown: 'KeyDown',
            focus: 'Focus'
        }
    },

    initComponent: function () {
        var me = this;
        me.callParent();
        if (me.deferHighlight) {
            me.setHighlightedItem =
                Ext.Function.createBuffered(me.setHighlightedItem, me.deferHighlight, me);
        }
    },

    addCmpEvents: function () {
        this.addEvents(
            /**
             * @event beforeitemmousedown
             * Fires before the mousedown event on an item is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'beforeitemmousedown',
            /**
             * @event beforeitemmouseup
             * Fires before the mouseup event on an item is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'beforeitemmouseup',
            /**
             * @event beforeitemmouseenter
             * Fires before the mouseenter event on an item is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'beforeitemmouseenter',
            /**
             * @event beforeitemmouseleave
             * Fires before the mouseleave event on an item is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'beforeitemmouseleave',
            /**
             * @event beforeitemclick
             * Fires before the click event on an item is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'beforeitemclick',
            /**
             * @event beforeitemdblclick
             * Fires before the dblclick event on an item is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'beforeitemdblclick',
            /**
             * @event beforeitemcontextmenu
             * Fires before the contextmenu event on an item is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'beforeitemcontextmenu',
            /**
             * @event beforeitemkeydown
             * Fires before the keydown event on an item is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object. Use {@link Ext.EventObject#getKey getKey()} to retrieve the key that was pressed.
             */
            'beforeitemkeydown',
            /**
             * @event itemmousedown
             * Fires when there is a mouse down on an item
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'itemmousedown',
            /**
             * @event itemmouseup
             * Fires when there is a mouse up on an item
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'itemmouseup',
            /**
             * @event itemmouseenter
             * Fires when the mouse enters an item.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'itemmouseenter',
            /**
             * @event itemmouseleave
             * Fires when the mouse leaves an item.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'itemmouseleave',
            /**
             * @event itemclick
             * Fires when an item is clicked.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'itemclick',
            /**
             * @event itemdblclick
             * Fires when an item is double clicked.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'itemdblclick',
            /**
             * @event itemcontextmenu
             * Fires when an item is right clicked.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object
             */
            'itemcontextmenu',
            /**
             * @event itemkeydown
             * Fires when a key is pressed while an item is currently selected.
             * @param {Ext.view.View} this
             * @param {Ext.data.Model} record The record that belongs to the item
             * @param {HTMLElement} item The item's element
             * @param {Number} index The item's index
             * @param {Ext.EventObject} e The raw event object. Use {@link Ext.EventObject#getKey getKey()} to retrieve the key that was pressed.
             */
            'itemkeydown',
            /**
             * @event beforecontainermousedown
             * Fires before the mousedown event on the container is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'beforecontainermousedown',
            /**
             * @event beforecontainermouseup
             * Fires before the mouseup event on the container is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'beforecontainermouseup',
            /**
             * @event beforecontainermouseover
             * Fires before the mouseover event on the container is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'beforecontainermouseover',
            /**
             * @event beforecontainermouseout
             * Fires before the mouseout event on the container is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'beforecontainermouseout',
            /**
             * @event beforecontainerclick
             * Fires before the click event on the container is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'beforecontainerclick',
            /**
             * @event beforecontainerdblclick
             * Fires before the dblclick event on the container is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'beforecontainerdblclick',
            /**
             * @event beforecontainercontextmenu
             * Fires before the contextmenu event on the container is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'beforecontainercontextmenu',
            /**
             * @event beforecontainerkeydown
             * Fires before the keydown event on the container is processed. Returns false to cancel the default action.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object. Use {@link Ext.EventObject#getKey getKey()} to retrieve the key that was pressed.
             */
            'beforecontainerkeydown',
            /**
             * @event containermouseup
             * Fires when there is a mouse up on the container
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'containermouseup',
            /**
             * @event containermouseover
             * Fires when you move the mouse over the container.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'containermouseover',
            /**
             * @event containermouseout
             * Fires when you move the mouse out of the container.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'containermouseout',
            /**
             * @event containerclick
             * Fires when the container is clicked.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'containerclick',
            /**
             * @event containerdblclick
             * Fires when the container is double clicked.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'containerdblclick',
            /**
             * @event containercontextmenu
             * Fires when the container is right clicked.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object
             */
            'containercontextmenu',
            /**
             * @event containerkeydown
             * Fires when a key is pressed while the container is focused, and no item is currently selected.
             * @param {Ext.view.View} this
             * @param {Ext.EventObject} e The raw event object. Use {@link Ext.EventObject#getKey getKey()} to retrieve the key that was pressed.
             */
            'containerkeydown',

            /**
             * @event
             * @inheritdoc Ext.selection.DataViewModel#selectionchange
             */
            'selectionchange',
            /**
             * @event
             * @inheritdoc Ext.selection.DataViewModel#beforeselect
             */
            'beforeselect',
            /**
             * @event
             * @inheritdoc Ext.selection.DataViewModel#beforedeselect
             */
            'beforedeselect',
            /**
             * @event
             * @inheritdoc Ext.selection.DataViewModel#select
             */
            'select',
            /**
             * @event
             * @inheritdoc Ext.selection.DataViewModel#deselect
             */
            'deselect',
            /**
             * @event
             * @inheritdoc Ext.selection.DataViewModel#focuschange
             */
            'focuschange',

            /**
             * @event highlightitem
             * Fires when a node is highlighted using keyboard navigation, or mouseover.
             * @param {Ext.view.View} view This View Component.
             * @param {Ext.Element} node The highlighted node.
             */
            'highlightitem',

            /**
             * @event unhighlightitem
             * Fires when a node is unhighlighted using keyboard navigation, or mouseout.
             * @param {Ext.view.View} view This View Component.
             * @param {Ext.Element} node The previously highlighted node.
             */
            'unhighlightitem'
        );
    },

    getFocusEl: function () {
        return this.getTargetEl();
    },

    // private
    afterRender: function () {
        var me = this;
        me.callParent();
        me.mon(me.getTargetEl(), {
            scope: me,
            /*
             * We need to make copies of this since some of the events fired here will end up triggering
             * a new event to be called and the shared event object will be mutated. In future we should
             * investigate if there are any issues with creating a new event object for each event that
             * is fired.
             */
            freezeEvent: true,
            click: me.handleEvent,
            mousedown: me.handleEvent,
            mouseup: me.handleEvent,
            dblclick: me.handleEvent,
            contextmenu: me.handleEvent,
            mouseover: me.handleEvent,
            mouseout: me.handleEvent,
            keydown: me.handleEvent
        });
    },

    handleEvent: function (e) {
        var me = this,
            key = e.type == 'keydown' && e.getKey();

        if (me.processUIEvent(e) !== false) {
            me.processSpecialEvent(e);
        }

        // After all listeners have processed the event, then unless the user is typing into an input field,
        // prevent browser's default action on SPACE which is to focus the event's target element.
        // Focusing causes the browser to attempt to scroll the element into view.
        if (key === e.SPACE) {
            if (!me.inputTagRe.test(e.getTarget().tagName)) {
                e.stopEvent();
            }
        }
    },

    // Private template method
    processItemEvent: Ext.emptyFn,
    processContainerEvent: Ext.emptyFn,
    processSpecialEvent: Ext.emptyFn,

    /*
     * Returns true if this mouseover/out event is still over the overItem.
     */
    stillOverItem: function (event, overItem) {
        var nowOver;

        // There is this weird bug when you hover over the border of a cell it is saying
        // the target is the table.
        // BrowserBug: IE6 & 7. If me.mouseOverItem has been removed and is no longer
        // in the DOM then accessing .offsetParent will throw an "Unspecified error." exception.
        // typeof'ng and checking to make sure the offsetParent is an object will NOT throw
        // this hard exception.
        if (overItem && typeof(overItem.offsetParent) === "object") {
            // mouseout : relatedTarget == nowOver, target == wasOver
            // mouseover: relatedTarget == wasOver, target == nowOver
            nowOver = (event.type == 'mouseout') ? event.getRelatedTarget() : event.getTarget();
            return Ext.fly(overItem).contains(nowOver);
        }

        return false;
    },

    processUIEvent: function (e) {
        var me = this,
            item = e.getTarget(me.getItemSelector(), me.getTargetEl()),
            map = this.statics().EventMap,
            index, record,
            type = e.type,
            overItem = me.mouseOverItem,
            newType;

        if (!item) {
            if (type == 'mouseover' && me.stillOverItem(e, overItem)) {
                item = overItem;
            }

            // Try to get the selected item to handle the keydown event, otherwise we'll just fire a container keydown event
            if (type == 'keydown') {
                record = me.getSelectionModel().getLastSelected();
                if (record) {
                    item = me.getNode(record);
                }
            }
        }

        if (item) {
            index = me.indexOf(item);
            if (!record) {
                record = me.getRecord(item);
            }

            // It is possible for an event to arrive for which there is no record... this
            // can happen with dblclick where the clicks are on removal actions (think a
            // grid w/"delete row" action column)
            if (!record || me.processItemEvent(record, item, index, e) === false) {
                return false;
            }

            newType = me.isNewItemEvent(item, e);
            if (newType === false) {
                return false;
            }

            if (
                (me['onBeforeItem' + map[newType]](record, item, index, e) === false) ||
                    (me.fireEvent('beforeitem' + newType, me, record, item, index, e) === false) ||
                    (me['onItem' + map[newType]](record, item, index, e) === false)
                ) {
                return false;
            }

            me.fireEvent('item' + newType, me, record, item, index, e);
        }
        else {
            if (
                (me.processContainerEvent(e) === false) ||
                    (me['onBeforeContainer' + map[type]](e) === false) ||
                    (me.fireEvent('beforecontainer' + type, me, e) === false) ||
                    (me['onContainer' + map[type]](e) === false)
                ) {
                return false;
            }

            me.fireEvent('container' + type, me, e);
        }

        return true;
    },

    isNewItemEvent: function (item, e) {
        var me = this,
            overItem = me.mouseOverItem,
            type = e.type;

        switch (type) {
            case 'mouseover':
                if (item === overItem) {
                    return false;
                }
                me.mouseOverItem = item;
                return 'mouseenter';

            case 'mouseout':
                // If the currently mouseovered item contains the mouseover target, it's *NOT* a mouseleave
                if (me.stillOverItem(e, overItem)) {
                    return false;
                }
                me.mouseOverItem = null;
                return 'mouseleave';
        }
        return type;
    },

    // private
    onItemMouseEnter: function (record, item, index, e) {
        if (this.trackOver) {
            this.highlightItem(item);
        }
    },

    // private
    onItemMouseLeave: function (record, item, index, e) {
        if (this.trackOver) {
            this.clearHighlight();
        }
    },

    // @private, template methods
    onItemMouseDown: Ext.emptyFn,
    onItemMouseUp: Ext.emptyFn,
    onItemFocus: Ext.emptyFn,
    onItemClick: Ext.emptyFn,
    onItemDblClick: Ext.emptyFn,
    onItemContextMenu: Ext.emptyFn,
    onItemKeyDown: Ext.emptyFn,
    onBeforeItemMouseDown: Ext.emptyFn,
    onBeforeItemMouseUp: Ext.emptyFn,
    onBeforeItemFocus: Ext.emptyFn,
    onBeforeItemMouseEnter: Ext.emptyFn,
    onBeforeItemMouseLeave: Ext.emptyFn,
    onBeforeItemClick: Ext.emptyFn,
    onBeforeItemDblClick: Ext.emptyFn,
    onBeforeItemContextMenu: Ext.emptyFn,
    onBeforeItemKeyDown: Ext.emptyFn,

    // @private, template methods
    onContainerMouseDown: Ext.emptyFn,
    onContainerMouseUp: Ext.emptyFn,
    onContainerMouseOver: Ext.emptyFn,
    onContainerMouseOut: Ext.emptyFn,
    onContainerClick: Ext.emptyFn,
    onContainerDblClick: Ext.emptyFn,
    onContainerContextMenu: Ext.emptyFn,
    onContainerKeyDown: Ext.emptyFn,
    onBeforeContainerMouseDown: Ext.emptyFn,
    onBeforeContainerMouseUp: Ext.emptyFn,
    onBeforeContainerMouseOver: Ext.emptyFn,
    onBeforeContainerMouseOut: Ext.emptyFn,
    onBeforeContainerClick: Ext.emptyFn,
    onBeforeContainerDblClick: Ext.emptyFn,
    onBeforeContainerContextMenu: Ext.emptyFn,
    onBeforeContainerKeyDown: Ext.emptyFn,

    //private
    setHighlightedItem: function (item) {
        var me = this,
            highlighted = me.highlightedItem;

        if (highlighted != item) {
            if (highlighted) {
                Ext.fly(highlighted).removeCls(me.overItemCls);
                me.fireEvent('unhighlightitem', me, highlighted);
            }

            me.highlightedItem = item;

            if (item) {
                //console.log(item.viewIndex);
                Ext.fly(item).addCls(me.overItemCls);
                me.fireEvent('highlightitem', me, item);
            }
        }
    },

    /**
     * Highlights a given item in the View. This is called by the mouseover handler if {@link #overItemCls}
     * and {@link #trackOver} are configured, but can also be called manually by other code, for instance to
     * handle stepping through the list via keyboard navigation.
     * @param {HTMLElement} item The item to highlight
     */
    highlightItem: function (item) {
        this.setHighlightedItem(item);
    },

    /**
     * Un-highlights the currently highlighted item, if any.
     */
    clearHighlight: function () {
        this.setHighlightedItem(undefined);
    },

    onUpdate: function (store, record) {
        var me = this,
            node,
            newNode,
            highlighted;

        if (me.viewReady) {
            node = me.getNode(record);
            newNode = me.callParent(arguments);
            highlighted = me.highlightedItem;

            if (highlighted && highlighted === node) {
                delete me.highlightedItem;
                if (newNode) {
                    me.highlightItem(newNode);
                }
            }
        }
    },

    refresh: function () {
        this.clearHighlight();
        this.callParent(arguments);
    }
});