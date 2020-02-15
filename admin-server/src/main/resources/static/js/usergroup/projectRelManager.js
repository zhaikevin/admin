var app = new Vue({
    el: "#app",
    data: {
        form: {
            projectName: '',
            projectCode: '',
        },
        centerDialogVisible: false,
        currentPage: 0,
        pageSize: 10,
        total: 0,
        sort: 'id',
        order: 'desc',
        listLoading: false,
        tableData: [],
        authData: {
            delete: false,
        },
        permission: false,
    },
    mounted() {
        this.fetchData();
        this.auth();
        this.validatePermission()
    },
    methods: {
        validatePermission() {
            this.permission = groupAuthentication(window.parent.app.projectRelId)
        },
        fetchData() {
            this.listLoading = true;
            var self = this;
            Vue.http.get('../../projectRel/list',
                {
                    params:
                        {
                            currentPage: self.currentPage,
                            pageSize: self.pageSize,
                            sort: self.sort,
                            order: self.order,
                            projectName: self.form.projectName,
                            projectCode: self.form.projectCode,
                            groupId: window.parent.app.projectRelId,
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
                self.$message.error('获取用户组关系列表失败');
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
        handleDelete(index, row) {
            var self = this;
            this.$confirm('确认删除该项目与用户组的关联关系？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(function () {
                Vue.http.post('../../projectRel/delete/' + row.id,
                    {}
                ).then(function (res) {
                    var data = res.body;
                    if (data.status === 0) {
                        self.$message.success("删除项目与用户组的关联关系成功")
                        self.fetchData()
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('删除项目与用户组的关联关系失败');
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
                'admin.userGroup.project.delete': 'delete',
            }
            authentication(authCode, this.authData, this)
        }
    }
});