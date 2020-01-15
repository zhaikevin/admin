var app = new Vue({
    el: "#app",
    data: {
        createForm: {
            username: '',
            password: '',
            email: '',
            mobile: '',
            state:1,
        },
        createRules: {
            state: [
                {required: true, message: '请选择状态', trigger: 'change'}
            ],
            username: [
                {required: true, message: '请输入用户名', trigger: 'blur'}
            ],
            password: [
                {required: true, message: '请输入密码', trigger: 'blur'}
            ],
            email: [
                {required: true, message: '请输入邮箱', trigger: 'blur'}
            ]
        }
    },
    methods: {
        submitForm(formName) {
            this.register = true
            var self = this;
            var form = this.$refs[formName];
            form.validate(function (valid) {
                if (!valid) {
                    return false;
                }
                Vue.http.post('../../user/create',
                    {
                        username: self.createForm.username,
                        password: self.createForm.password,
                        email: self.createForm.email,
                        mobile: self.createForm.mobile,
                        state: self.createForm.state
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
                    self.$message.error('创建用户异常')
                })
            })
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    }
});
