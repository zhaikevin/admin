var app = new Vue({
    el: "#app",
    data: {
        defaultProps: {
            children: 'children',
            label: 'name'
        },
        data: [],
        authData: {
            create: false,
        },
    },
    created() {
        this.fetchData();
        this.auth();
    },
    mounted() {
        this.getMenuId();
    },
    methods: {
        getMenuId() {
            var self = this;
            Vue.http.get('../../authentication/getMenuId',
                {
                    params: {
                        roleId: window.parent.app.authenticationId
                    }
                }
            ).then(function (res) {
                var data = res.body
                if (data.status === 0) {
                    self.$refs.tree.setCheckedKeys(data.data)
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取菜单异常')
            });
        },
        fetchData() {
            var self = this;
            Vue.http.get('../../menu/all',
                {
                    params: {
                        state: 1
                    }
                }
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
        clickMenu(currentObj, treeStatus) {
            // 用于：父子节点严格互不关联时，父节点勾选变化时通知子节点同步变化，实现单向关联。
            var selected = treeStatus.checkedKeys.indexOf(currentObj.id) // -1未选中
            // 选中
            if (selected !== -1) {
                // 子节点只要被选中父节点就被选中
                this.selectedParent(currentObj)
                // 统一处理子节点为相同的勾选状态
                this.uniteChildSame(currentObj, true)
            } else {
                // 未选中 处理子节点全部未选中
                if (currentObj.children != undefined && currentObj.children.length !== 0) {
                    this.uniteChildSame(currentObj, false)
                }
            }
        },
        // 统一处理子节点为相同的勾选状态
        uniteChildSame(currentObj, isSelected) {
            this.$refs.tree.setChecked(currentObj.id, isSelected)
            if (currentObj.children != undefined && currentObj.children.length !== 0) {
                for (var i = 0; i < currentObj.children.length; i++) {
                    this.uniteChildSame(currentObj.children[i], isSelected)
                }
            }
        },
        // 统一处理父节点为选中
        selectedParent(currentObj) {
            var currentNode = this.$refs.tree.getNode(currentObj)
            if (currentNode.parent.key !== undefined) {
                this.$refs.tree.setChecked(currentNode.parent, true)
                this.selectedParent(currentNode.parent)
            }
        },

        save() {
            var self = this
            var nodes = self.$refs.tree.getCheckedNodes(false, true)
            var list = []
            for (var i = 0; i < nodes.length; i++) {
                list.push({
                    roleId: window.parent.app.authenticationId,
                    menuId: nodes[i].id
                })
            }
            Vue.http.post('../../authentication/save',
                {
                    authentications: JSON.stringify(list),
                    roleId: window.parent.app.authenticationId,
                }
            ).then(function (res) {
                var data = res.body;
                if (data.status === 0) {
                    //self.$message.success("保存权限信息成功")
                    window.parent.app.authenticationDialog = false
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('保存权限信息异常');
            });
        },

        auth: function () {
            var authCode = {
                'admin.role.authentication.create': 'create',
            }
            authentication(authCode, this.authData, this)
        }
    }
});