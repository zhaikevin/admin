var app = new Vue({
    el: "#app",
    data: {
        form: {
            username: ''
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
            Vue.http.get('../../user/listByRole',
                {
                    params:
                        {
                            currentPage: self.currentPage,
                            pageSize: self.pageSize,
                            username: self.form.username,
                            sort: self.sort,
                            order: self.order,
                            roleId:window.parent.app.addUserId
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
                self.$message.error('获取用户列表失败');
            });
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