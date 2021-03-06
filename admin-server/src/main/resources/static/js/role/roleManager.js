var app = new Vue({
    el: "#app",
    data: {
        form: {
            name: '',
        },
        centerDialogVisible: false,
        currentPage: 0,
        pageSize: 10,
        total: 0,
        sort: 'id',
        order: 'desc',
        listLoading: false,
        tableData: [],
        createDialog: false,
        createUrl: '',
        modifyDialog: false,
        modifyUrl: '',
        modifyId: 0,
        userRoleDialog: false,
        userRoleUrl: '',
        userRoleId: 0,
        addUserDialog: false,
        addUserUrl: '',
        addUserId: 0,
        authenticationDialog: false,
        authenticationUrl: '',
        authenticationId: 0,
        authData: {
            create: false,
            modify: false,
            delete: false,
            search: false,
            authentication: false,
            userList: false,
            userCreate: false,
        },
    },
    mounted() {
        this.fetchData();
        this.auth();
    },
    methods: {
        fetchData() {
            this.listLoading = true;
            var self = this;
            Vue.http.get('../../role/list',
                {
                    params:
                        {
                            currentPage: self.currentPage,
                            pageSize: self.pageSize,
                            name: self.form.name,
                            sort: self.sort,
                            order: self.order
                        }
                }
            ).then(function (res) {
                self.listLoading = false
                var data = res.body;
                if (data.status === 0) {
                    self.tableData = data.data.dataset
                    self.total = data.data.total
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.listLoading = false
                self.$message.error('获取角色列表失败');
            });
        },
        formatterDate(row, column, cellValue, index) {
            if (!cellValue) {
                return ''
            }
            var format = '{y}-{m}-{d} {h}:{i}:{s}'
            var date
            if (typeof cellValue === 'object') {
                date = cellValue
            } else {
                if (('' + cellValue).length === 10) cellValue = parseInt(cellValue) * 1000
                date = new Date(cellValue)
            }
            var formatObj = {
                y: date.getFullYear(),
                m: date.getMonth() + 1,
                d: date.getDate(),
                h: date.getHours(),
                i: date.getMinutes(),
                s: date.getSeconds(),
                a: date.getDay()
            }
            var time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, function (result, key) {
                var value = formatObj[key]
                // Note: getDay() returns 0 on Sunday
                if (key === 'a') {
                    return ['日', '一', '二', '三', '四', '五', '六'][value]
                }
                if (result.length > 0 && value < 10) {
                    value = '0' + value
                }
                return value || 0
            })
            return time_str
        },
        beforeCreateCloseDialog() {
            this.createUrl = ''
        },
        showCreateDialog() {
            this.createDialog = true
            this.createUrl = 'roleCreate.html?new=' + Math.random()
        },
        showModifyDialog(index, row) {
            this.modifyDialog = true
            this.modifyUrl = 'roleModify.html?new=' + Math.random()
            this.modifyId = row.id;
        },
        beforeModifyCloseDialog() {
            this.modifyId = 0
            this.modifyUrl = ''
        },
        showUserRoleDialog(index, row) {
            this.userRoleDialog = true
            this.userRoleUrl = 'userRoleManager.html?new=' + Math.random()
            this.userRoleId = row.id;
        },
        beforeUserRoleCloseDialog() {
            this.userRoleId = 0
            this.userRoleUrl = ''
        },
        showAddUserDialog(index, row) {
            this.addUserDialog = true
            this.addUserUrl = 'userRoleCreate.html?new=' + Math.random()
            this.addUserId = row.id;
        },
        beforeAddUserCloseDialog() {
            this.addUserId = 0
            this.addUserUrl = ''
        },
        showAuthenticationDialog(index, row) {
            this.authenticationDialog = true
            this.authenticationUrl = 'authenticationManager.html?new=' + Math.random()
            this.authenticationId = row.id;
        },
        beforeAuthenticationCloseDialog() {
            this.authenticationId = 0
            this.authenticationUrl = ''
        },
        handleDelete(index, row) {
            var self = this;
            this.$confirm('确认删除该角色及其关联权限？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(function () {
                Vue.http.post('../../role/delete/' + row.id,
                    {
                        params: {}
                    }
                ).then(function (res) {
                    var data = res.body;
                    if (data.status === 0) {
                        self.$message.success("删除角色成功")
                        self.fetchData()
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('删除角色失败');
                });
            }, function () {
                return
            })
        },
        onQuerySubmit: function () {
            this.fetchData()
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.fetchData();
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.fetchData();
        },
        auth: function () {
            var authCode = {
                'admin.role.create': 'create',
                'admin.role.search': 'search',
                'admin.role.modify': 'modify',
                'admin.role.delete': 'delete',
                'admin.role.authentication': 'authentication',
                'admin.role.user.list': 'userList',
                'admin.role.user.create': 'userCreate',
            }
            authentication(authCode, this.authData, this)
        }
    }
});