var app = new Vue({
    el: "#app",
    data: {
        defaultProps: {
            children: 'children',
            label: 'name'
        },
        data: [],
        createDialog: false,
        createUrl: '',
        modifyDialog: false,
        modifyUrl: '',
        checkedId: 0,
        checkedName: '',
        createDisabled: false,
        modifyDisabled: true,
        authData: {
            create: false,
            modify: false,
            delete: false,
        },
    },
    mounted() {
        this.fetchData();
        this.auth();
    },
    methods: {
        fetchData() {
            var self = this;
            Vue.http.get('../../menu/all',
                {}
            ).then(function (res) {
                var data = res.body
                if (data.status === 0) {
                    self.data = data.data
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取菜单异常')
            });
        },
        handleClick(data, checked, node) {
            if (checked) {
                this.$refs.tree.setCheckedNodes([data])
            }
            var nodes = this.$refs.tree.getCheckedNodes()
            if (nodes.length > 0) {
                var node = nodes[0]
                this.checkedId = node.id
                this.checkedName = node.name
                if (node.type === 2) {
                    this.createDisabled = true
                } else {
                    this.createDisabled = false
                }
                this.modifyDisabled = false
            } else {
                this.checkedId = 0
                this.checkedName = ''
                this.createDisabled = false
                this.modifyDisabled = true
            }
        },
        beforeCreateCloseDialog() {
            this.createUrl = ''
        },
        showCreateDialog() {
            this.createDialog = true
            this.createUrl = 'menuCreate.html?new=' + Math.random()
        },
        showModifyDialog() {
            this.modifyDialog = true
            this.modifyUrl = 'menuModify.html?new=' + Math.random()
        },
        beforeModifyCloseDialog() {
            this.modifyUrl = ''
        },
        deleteMenu() {
            var self = this
            this.$confirm('此操作将删除该菜单和子菜单，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(function () {
                Vue.http.post('../../menu/delete/' + self.checkedId,
                    {}
                ).then(function (res) {
                    var data = res.body;
                    if (data.status === 0) {
                        self.fetchData()
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('删除菜单异常');
                });
            }, function () {
                return
            })
        },
        auth: function () {
            var authCode = {
                'admin.menu.create': 'create',
                'admin.menu.modify': 'modify',
                'admin.menu.delete': 'delete',
            }
            authentication(authCode, this.authData, this)
        }
    }
});