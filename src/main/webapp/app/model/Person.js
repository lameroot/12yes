Ext.define('Yes.model.Person',{
    name: 'unknown',

    config: {
        title: "tutklle"
    },

    constructor: function(name) {
        if (name) {
            this.name = name;
        }
    },

    eat: function(food) {
        return name + " are eating " + food;
    },

    applyTitle: function(title) {
        if ( title.length > 1 ) {
            //debugger;
            console.log('title = ' + title);
        }
        else {
            return title;
        }

    }
});
