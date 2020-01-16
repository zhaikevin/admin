var app = new Vue({
    el: "#app",
    data() {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.resetPasswordForm.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        }
        return {
            resetPasswordForm: {
                id: 0,
                username: '',
                password: '',
                confirmPassword: '',
            },
            resetPasswordRules: {
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'}
                ],
                confirmPassword: [
                    {required: true, message: '请重新输入密码', trigger: 'blur'},
                    {validator: validatePass, trigger: 'blur'}
                ],
            }
        }
    },
    mounted() {
        this.fetchUserInfo();
    },
    methods: {
        fetchUserInfo() {
            var self = this;
            Vue.http.get('../../user/getById',
                {
                    params:
                        {
                            id: window.parent.app.resetPasswordId
                        }
                }
            ).then(function (res) {
                var data = res.body;
                if (data.status === 0) {
                    self.resetPasswordForm.id = data.data.id
                    self.resetPasswordForm.username = data.data.username
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取用户信息异常');
            });
        },
        submitForm(formName) {
            this.register = true
            var self = this;
            var form = this.$refs[formName];
            form.validate(function (valid) {
                if (!valid) {
                    return false;
                }
                Vue.http.post('../../user/resetPassword',
                    {
                        id: self.resetPasswordForm.id,
                        password: self.resetPasswordForm.password,
                    }
                ).then(function (res) {
                    var data = res.body
                    if (data.status === 0) {
                        window.parent.app.resetPasswordDialog = false
                        window.parent.app.fetchData()
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('重置密码异常')
                })
            })
        },
        cancel() {
            window.parent.app.resetPasswordDialog = false
        }
    }
});
