var app = new Vue({
    el: "#app",
    data: {
        username: 'kevin',
        isCollapse: false,
        menuList: [],
        headerMenuList: [],
        currentBaseMenuId: 1,
        editableTabs:[],
        editableTabsValue:''
    },
    mounted() {
        this.getHeaderMenuList();
        this.getMenuList();
    },
    methods: {
        getHeaderMenuList() {
            var self = this;
            Vue.http.get('./menu/getBaseMenu',
                {}
            ).then(function (res) {
                var data = res.body;
                if (data.status === 0) {
                    self.headerMenuList = data.data
                    if(data.data.length > 0) {
                        self.currentBaseMenuId = data.data[0].id
                    }
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取菜单异常');
            });
        },
        getMenuList() {
            var self = this;
            Vue.http.get('./menu/getByParentId',
                {
                    params:
                        {
                            parentId: this.currentBaseMenuId
                        }
                }
            ).then(function (res) {
                var data = res.body;
                if (data.status === 0) {
                    self.menuList = data.data
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取菜单异常');
            });
        },
        collapse() {
            this.isCollapse = !this.isCollapse
        },
        fullScreen() {
            var isFull = document.isFullScreen || document.mozIsFullScreen || document.webkitIsFullScreen
            // 判断是否全屏
            if (isFull) {
                var close = document.exitFullscreen || document.webkitCancelFullScreen || document.mozCancelFullScreen || document.msExitFullscreen
                close && close.call(document)
            } else {
                var docElm = document.documentElement
                var open = docElm.requestFullScreen || docElm.webkitRequestFullScreen || docElm.mozRequestFullScreen || docElm.msRequestFullscreen
                open && open.call(docElm)
            }
        },
        handleCommand (command) {
            switch (command) {

            }
        },
        handleSelect(index) {
            this.currentBaseMenuId = index
            this.getMenuList()
        },
        handleMenuSelect(index) {
            var self = this;
            Vue.http.get('./menu/getById',
                {
                    params:
                        {
                            menuId: index
                        }
                }
            ).then(function (res) {
                var data = res.body;
                if (data.status === 0) {
                    var menu = data.data
                    var flag = true
                    var tabs = self.editableTabs
                    tabs.forEach(function (tab, index) {
                        if(tab.code === menu.code) {
                            //如果打开则选中
                            self.editableTabsValue = tab.code
                            flag = false
                            return
                        }
                    })
                    if (flag) {
                        //如果没有打开则添加
                        var newTabCode = menu.code
                        self.editableTabs.push({
                            title: menu.name,
                            code: newTabCode,
                            url: menu.url
                        })
                        self.editableTabsValue = newTabCode
                    }
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取菜单异常');
            });
        },
        //标签页移除
        removeTab(targetCode) {
            var tabs = this.editableTabs
            var activeCode = this.editableTabsValue
            if (activeCode === targetCode) {
                if(tabs.length > 0) {
                    activeCode = tabs[0].code
                }
            }
            this.editableTabsValue = activeCode
            this.editableTabs = tabs.filter(function (tab) {
                tab.code != targetCode
            })
        }
    }
});
