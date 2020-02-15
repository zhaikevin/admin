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
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        fetchData() {
            this.listLoading = true;
            var self = this;
            Vue.http.get('../../project/listByGroup',
                {
                    params:
                        {
                            currentPage: self.currentPage,
                            pageSize: self.pageSize,
                            projectName: self.form.projectName,
                            projectCode: self.form.projectCode,
                            sort: self.sort,
                            order: self.order,
                            groupId: window.parent.app.addProjectId,
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
                self.$message.error('获取项目列表失败');
            });
        },
        onQuerySubmit: function () {
            this.fetchData()
        },
        handleAdd(index, row) {
            var self = this;
            this.$confirm('确认添加该项目与用户组的关联关系？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(function () {
                Vue.http.post('../../projectRel/save',
                    {
                        projectId: row.id,
                        groupId: window.parent.app.addProjectId,
                    }
                ).then(function (res) {
                    var data = res.body;
                    if (data.status === 0) {
                        self.$message.success("添加项目与用户组的关联关系成功")
                        self.fetchData()
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('添加项目与用户组的关联关系失败');
                });
            }, function () {
                return
            })
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