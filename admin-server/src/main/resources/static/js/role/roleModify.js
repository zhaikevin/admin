var app = new Vue({
    el: "#app",
    data: {
        modifyForm: {
            id:0,
            name: '',
            remark: '',
        },
        modifyRules: {
            name: [
                {required: true, message: '请输入角色名称', trigger: 'blur'}
            ],
            remark: [
                {max: 500, message: '最大长度为255', trigger: 'blur'}
            ]
        }
    },
    mounted() {
        this.fetchInfo();
    },
    methods: {
        fetchInfo() {
            var self = this;
            Vue.http.get('../../role/getById',
                {
                    params:
                        {
                            id: window.parent.app.modifyId
                        }
                }
            ).then(function (res) {
                var data = res.body;
                if (data.status === 0) {
                    self.modifyForm.id = data.data.id
                    self.modifyForm.name = data.data.name
                    self.modifyForm.remark = data.data.remark
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取角色信息异常');
            });
        },
        submitForm(formName) {
            var self = this;
            var form = this.$refs[formName];
            form.validate(function (valid) {
                if (!valid) {
                    return false;
                }
                Vue.http.post('../../role/modify',
                    {
                        id: self.modifyForm.id,
                        name: self.modifyForm.name,
                        remark: self.modifyForm.remark,
                    }
                ).then(function (res) {
                    var data = res.body
                    if (data.status === 0) {
                        window.parent.app.modifyDialog = false
                        window.parent.app.fetchData()
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('编辑角色异常')
                })
            })
        },
        cancel() {
            window.parent.app.modifyDialog = false
        }
    }
});
