var app = new Vue({
    el: "#app",
    data: {
        username: 'kevin',
        isCollapse: false,
        menuList: [],
        headerMenuList: [],
        currentBaseMenuId: 1
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
            let isFull = document.isFullScreen || document.mozIsFullScreen || document.webkitIsFullScreen
            // 判断是否全屏
            if (isFull) {
                let close = document.exitFullscreen || document.webkitCancelFullScreen || document.mozCancelFullScreen || document.msExitFullscreen
                close && close.call(document)
            } else {
                let docElm = document.documentElement
                let open = docElm.requestFullScreen || docElm.webkitRequestFullScreen || docElm.mozRequestFullScreen || docElm.msRequestFullscreen
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
        }
    }
});
