var app = new Vue({
    el: "#app",
    data: {
        createForm: {
            name: '',
            code:'',
            remark: '',
        },
        createRules: {
            name: [
                {required: true, message: '请输入用户组名称', trigger: 'blur'}
            ],
            code: [
                {required: true, message: '请输入用户组编码', trigger: 'blur'}
            ],
            remark: [
                {max: 500, message: '最大长度为255', trigger: 'blur'}
            ]
        }
    },
    methods: {
        submitForm(formName) {
            var self = this;
            var form = this.$refs[formName];
            form.validate(function (valid) {
                if (!valid) {
                    return false;
                }
                Vue.http.post('../../userGroup/create',
                    {
                        name: self.createForm.name,
                        code: self.createForm.code,
                        remark: self.createForm.remark,
                    }
                ).then(function (res) {
                    var data = res.body
                    if (data.status === 0) {
                        window.parent.app.createDialog = false
                        window.parent.app.fetchData()
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('创建用户组异常')
                })
            })
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    }
});
