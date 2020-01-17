var app = new Vue({
    el: "#app",
    data: {
        modifyForm: {
            id:0,
            name: '',
            url: '',
            remark: '',
        },
        modifyRules: {
            name: [
                {required: true, message: '请输入系统名称', trigger: 'blur'}
            ],
            url: [
                {required: true, message: '请输入系统链接', trigger: 'blur'}
            ],
            remark: [
                {max: 500, message: '最大长度为255', trigger: 'blur'}
            ]
        }
    },
    mounted() {
        this.fetchSystemInfo();
    },
    methods: {
        fetchSystemInfo() {
            var self = this;
            Vue.http.get('../../system/getById',
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
                    self.modifyForm.url = data.data.url
                    self.modifyForm.remark = data.data.remark
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取系统信息异常');
            });
        },
        submitForm(formName) {
            var self = this;
            var form = this.$refs[formName];
            form.validate(function (valid) {
                if (!valid) {
                    return false;
                }
                Vue.http.post('../../system/modify',
                    {
                        id: self.modifyForm.id,
                        name: self.modifyForm.name,
                        url: self.modifyForm.url,
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
                    self.$message.error('编辑系统异常')
                })
            })
        },
        cancel() {
            window.parent.app.modifyDialog = false
        }
    }
});
