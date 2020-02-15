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
        userGroupRelDialog: false,
        userGroupRelUrl: '',
        userGroupRelId: 0,
        addUserDialog: false,
        addUserUrl: '',
        addUserId: 0,
        projectRelDialog: false,
        projectRelUrl: '',
        projectRelId: 0,
        addProjectDialog: false,
        addProjectUrl: '',
        addProjectId: 0,
        authData: {
            create: false,
            modify: false,
            delete: false,
            search: false,
            userList: false,
            userCreate: false,
            projectList: false,
            projectCreate: false,
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
            Vue.http.get('../../userGroup/list',
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
                self.$message.error('获取用户组列表失败');
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
            this.createUrl = 'userGroupCreate.html?new=' + Math.random()
        },
        showModifyDialog(index, row) {
            this.modifyDialog = true
            this.modifyUrl = 'userGroupModify.html?new=' + Math.random()
            this.modifyId = row.id;
        },
        beforeModifyCloseDialog() {
            this.modifyId = 0
            this.modifyUrl = ''
        },
        showUserGroupRelDialog(index, row) {
            this.userGroupRelDialog = true
            this.userGroupRelUrl = 'userGroupRelManager.html?new=' + Math.random()
            this.userGroupRelId = row.id;
        },
        beforeUserGroupRelCloseDialog() {
            this.userGroupRelId = 0
            this.userGroupRelUrl = ''
        },
        showAddUserDialog(index, row) {
            if(!groupAuthentication(row.id)) {
                this.$message.error('你不是该群组管理员，无权操作')
                return
            }
            this.addUserDialog = true
            this.addUserUrl = 'userGroupRelCreate.html?new=' + Math.random()
            this.addUserId = row.id;
        },
        beforeAddUserCloseDialog() {
            this.addUserId = 0
            this.addUserUrl = ''
        },
        showProjectRelDialog(index, row) {
            this.projectRelDialog = true
            this.projectRelUrl = 'projectRelManager.html?new=' + Math.random()
            this.projectRelId = row.id;
        },
        beforeProjectRelCloseDialog() {
            this.projectRelId = 0
            this.projectRelUrl = ''
        },
        showAddProjectDialog(index, row) {
            if(!groupAuthentication(row.id)) {
                this.$message.error('你不是该群组管理员，无权操作')
                return
            }
            this.addProjectDialog = true
            this.addProjectUrl = 'projectRelCreate.html?new=' + Math.random()
            this.addProjectId = row.id;
        },
        beforeAddProjectCloseDialog() {
            this.addProjectId = 0
            this.addProjectUrl = ''
        },
        handleDelete(index, row) {
            var self = this;
            this.$confirm('确认删除该用户组？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(function () {
                Vue.http.post('../../userGroup/delete/' + row.id,
                    {
                    }
                ).then(function (res) {
                    var data = res.body;
                    if (data.status === 0) {
                        self.$message.success("删除用户组成功")
                        self.fetchData()
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('删除用户组失败');
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
                'admin.userGroup.create': 'create',
                'admin.userGroup.search': 'search',
                'admin.userGroup.modify': 'modify',
                'admin.userGroup.delete': 'delete',
                'admin.userGroup.user.list': 'userList',
                'admin.userGroup.user.create': 'userCreate',
                'admin.userGroup.project.list': 'projectList',
                'admin.userGroup.project.create': 'projectCreate',
            }
            authentication(authCode, this.authData, this)
        }
    }
});