Ext.onReady(function () {
    var win,
        button = Ext.get('show-btn');


    addbutton = Ext.get('add-btn');


    var checkboxext = Ext.create('Ext.form.Panel', {
        title: 'Pizza Order',
        items: [
            {
                xtype: 'fieldcontainer',
                fieldLabel: 'Toppings',
                defaultType: 'checkboxfield',
                items: [
                    {
                        boxLabel: 'Anchovies',
                        name: 'topping',
                        inputValue: '1',
                        id: 'checkbox1'
                    }, {
                        boxLabel: 'Artichoke Hearts',
                        name: 'topping',
                        inputValue: '2',
                        checked: true,
                        id: 'checkbox2'
                    }, {
                        boxLabel: 'Bacon',
                        name: 'topping',
                        inputValue: '3',
                        id: 'checkbox3'
                    }
                ]
            }
        ],
        bbar: [
            {
                text: 'Select Bacon',
                handler: function () {
                    Ext.getCmp('checkbox3').setValue(true);
                }
            },
            '-',
            {
                text: 'Select All',
                handler: function () {
                    Ext.getCmp('checkbox1').setValue(true);
                    Ext.getCmp('checkbox2').setValue(true);
                    Ext.getCmp('checkbox3').setValue(true);
                }
            },
            {
                text: 'Deselect All',
                handler: function () {
                    Ext.getCmp('checkbox1').setValue(false);
                    Ext.getCmp('checkbox2').setValue(false);
                    Ext.getCmp('checkbox3').setValue(false);
                }
            }
        ]
    });


    var label = Ext.create('Ext.form.Label', {
        text: '提醒',
        listeners: {
            render: function () {
                Ext.fly(this.el).on('click', function (e, t) {
                    alert(111);
                });
            }
        },
        scope: this
    });

    urlss = 'http://127.0.0.1:8080/bse-business-web/zsource/productDefineIndex.action?standalone&exptoken=NDY5QzcyNUFBMEYyRENFOTIyNTk1MEU2M0IxNDcxNDIsZTI1NTczYWUtYjE5Ni00ZDRmLTk4OTEtNWVjMWFjODMwMjAwLDA4NDU0NCxXMDExMzAyMDIwNTE1LOS4iua1t+mXteihjOWMuua1puaxn+mVh+iQpeS4mumDqCwxNDkwMjU1NDc0OTkx';
    urlcc = 'http://10.226.96.104:8080/bse-business-web/zsource/commonSelectorTestPage.action?standalone&exptoken=NDY5QzcyNUFBMEYyRENFOTIyNTk1MEU2M0IxNDcxNDIsZTI1NTczYWUtYjE5Ni00ZDRmLTk4OTEtNWVjMWFjODMwMjAwLDA4NDU0NCxXMDExMzAyMDIwNTE1LOS4iua1t+mXteihjOWMuua1puaxn+mVh+iQpeS4mumDqCwxNDkwMjU1NDc0OTkx';
    url = 'www.baidu.com';

    addbutton.on('click', function () {

        if (win) {
            Ext.getCmp("tabpannel").add({
                xtype: 'panel',
                title: 'Tab 1',
                html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="' + 'url' + '"> </iframe>',
                //http://www.baidu.com
                /*
                 loader: {
                 url: 'www.baidu.com',
                 contentType: 'html',
                 loadMask: true
                 },
                 listeners: {
                 activate: function(tab) {
                 tab.loader.load();
                 }
                 },
                 */

                closable: true
            });

        }

    });


    button.on('click', function () {

        if (!win) {
            win = Ext.create('widget.window', {
                title: 'addtab',
                header: {
                    titlePosition: 2,
                    titleAlign: 'left'
                },
                closable: true,
                closeAction: 'hide',
                width: 600,
                minWidth: 350,
                height: 350,
                tools: [{type: 'pin'}],
                layout: {
                    type: 'border',
                    padding: 5
                },
                getCheckboxext: function () {
                    return checkboxext;
                },
                items: [{
                    id: 'tabpannel',
                    region: 'center',
                    xtype: 'tabpanel',
                    items: [{
                        // LTR even when example is RTL so that the code can be read
                        rtl: false,
                        title: '一',
                        items: [label]
                        //html: '<p>Window configured with:</p><pre style="margin-left:20px"><code>header: {\n    titlePosition: 2,\n    titleAlign: "center"\n},\ntools: [{type: "pin"}],\nclosable: true</code></pre>'
                    }, {
                        title: '二',
                        listeners: {activate: handleActivate},
                        html: 'Hello world 2'
                    }, {
                        //cls:  'myCls',
                        //style:'color:red;background:green;' ,
                        //draggable: true,
                        title: '三',
                        //html: 'Hello world 3',
                        items:[
                            {
                                xtype:"radio",
                                name:"sex",
                                fieldLabel:"性别",
                                boxLabel:"男"
                            }, {
                                xtype: "checkboxfield",
                                name: "swim",
                                fieldLabel: "爱好",
                                boxLabel: "游泳"
                            }


                        ],
                        //items: [checkboxext],
                        closable: true

                    }, {
                        title: '四',
                        layout: 'fit',
                        height: 375,
                        id: 'training_grief_rep_tab_4',
                        border: false,
                        deferredRender: false,
//items : new TrainingBriefSummaryItem({sunmmaryId: tariningId}), 
                        autoScroll: true,
                        html: ' <iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="' + 'http://www.baidu.com' + '"> </iframe>'
                    }
                        //,{
                        //title: 'AJAX��Ϣ',
                        //autoLoad: { url: 'AjaxTabContent', params: { data: "�ӿͻ��˴���Ĳ���" }, method: 'GET' }  
                        //}
                    ]
                }]
            });
        }
        button.dom.disabled = true;
        if (win.isVisible()) {
            win.hide(this, function () {
                button.dom.disabled = false;
            });
        } else {
            win.show(this, function () {
                button.dom.disabled = false;
            });
        }
    });


    function handleActivate(tab) {
        alert(tab.title + ': activated提醒');
    };


});




