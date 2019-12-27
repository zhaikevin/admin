var app = new Vue({
    el: "#app",
    data: {
        username: Cookies.get('user_name'),
        isCollapse: false,
        menuList: [],
        headerMenuList: [],
        currentBaseMenuId: 1,
        editableTabs:[],
        editableTabsValue:'',
        activeIndex:'',
        showMain:false,
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
        handleCommand (command) {
            switch (command) {
                case 'logout':this.logout()
            }
        },
        //退出登录
        logout() {
            var self = this;
            this.$confirm('确认要退出登录？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                Vue.http.post('./user/logout',
                    {

                    }
                ).then(function (res) {
                    var data = res.body;
                    if (data.status === 0) {
                        window.location.href="login.html"
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('退出登录异常');
                });
            }, function () {
                return
            })
        },
        handleSelect(index) {
            this.setBaseMenu(index)
        },
        handleMenuSelect(index) {
            var self = this;
            Vue.http.get('./menu/getByCode',
                {
                    params:
                        {
                            code: index
                        }
                }
            ).then(function (res) {
                var data = res.body;
                if (data.status === 0) {
                    self.showMain = true
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
                            url: menu.url,
                            baseMenuId: self.currentBaseMenuId
                        })
                        self.editableTabsValue = newTabCode
                    }
                    self.setActiveIndex(self.editableTabsValue)
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取菜单异常');
            });
        },
        //标签页移除
        removeTab(targetCode) {
            var self = this
            var tabs = this.editableTabs.filter(function (tab) {
                return tab.code != targetCode
            })
            var activeCode = this.editableTabsValue
            if (activeCode === targetCode) {
                if(tabs.length > 0) {
                    activeCode = tabs[tabs.length-1].code
                } else {
                    activeCode = ''
                    self.showMain = false
                }
            }
            this.editableTabsValue = activeCode
            this.setActiveIndex(activeCode)
            this.editableTabs = tabs
        },
        //点击标签页
        clickTab(targetCode) {
            this.setActiveIndex(targetCode.name)
        },
        setBaseMenu(index) {
            this.currentBaseMenuId = index
            this.getMenuList()
        },
        setActiveIndex(index) {
            var tabs = this.editableTabs
            var self = this
            tabs.forEach(function (tab) {
                if(tab.code === index) {
                    self.setBaseMenu(tab.baseMenuId)
                    return
                }
            })
            this.activeIndex = index;
        }
    }
});
