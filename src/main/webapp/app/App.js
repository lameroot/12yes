Ext.application({
    name: 'Yes',

    launch: function() {
        Ext.create('Ext.container.Viewport',{
            layout: 'fit',
            items: [
                {
                    title: 'Hello222',
                    html: this.getPerson().eat('tetst')
                }
            ]
        });
    },

    getPerson: function() {
        var person = Ext.create('Yes.model.Person','stas',{
            title: 'hahaha'
        });
//        alert(person.getTitle());
        //person.title = "hhhhh";
        return person;
    }
});