var app = new Vue({
    el: "#app",
    data: {
        form: {
            username: '',
            state: ''
        },
        centerDialogVisible: false,
        currentPage: 0,
        pageSize: 10,
        total: 0,
        sort: 'id',
        order: 'desc',
        listLoading: false,
        tableData: [],
        workerIpOptions: [],
        zoneCodeOptions: [],
        stateOptions: [
            {'label': '全部', 'val': ''},
            {'label': '禁用', 'val': '0'},
            {'label': '正常', 'val': '1'}
        ],
        createDialog: false,
        createUrl: '',
        modifyDialog: false,
        modifyUrl: '',
        modifyId: 0,
        resetPasswordDialog: false,
        resetPasswordUrl: '',
        resetPasswordId: 0,
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        fetchData() {
            this.listLoading = true;
            var self = this;
            Vue.http.get('../../user/list',
                {
                    params:
                        {
                            currentPage: self.currentPage,
                            pageSize: self.pageSize,
                            username: self.form.username,
                            state: self.form.state,
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
                self.$message.error('请求数据失败,请检查网络');
            });
        },
        formatterState(row, column, cellValue, index) {
            if (row.state == 0) {
                return '禁用'
            } else if (row.state == 1) {
                return '正常'
            }
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
            this.createUrl = 'userCreate.html?new=' + Math.random()
        },
        showModifyDialog(index, row) {
            this.modifyDialog = true
            this.modifyUrl = 'userModify.html?new=' + Math.random()
            this.modifyId = row.id;
        },
        beforeModifyCloseDialog() {
            this.modifyId = 0
            this.modifyUrl = ''
        },
        resetPassword(index, row) {
            this.resetPasswordDialog = true
            this.resetPasswordUrl = 'resetPassword.html?new=' + Math.random()
            this.resetPasswordId = row.id;
        },
        beforeResetPasswordCloseDialog() {
            this.resetPasswordId = 0
            this.resetPasswordUrl = ''
        },
        handleDelete(index, row) {
            var self = this;
            this.$confirm('确认删除该用户？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(function () {
                Vue.http.post('../../user/delete/' + row.id,
                    {
                        params: {}
                    }
                ).then(function (res) {
                    var data = res.body;
                    if (data.status === 0) {
                        self.$message.success("删除用户成功")
                        self.fetchData()
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('删除用户失败');
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
        }
    }
});