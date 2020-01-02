var app = new Vue({
    el: "#app",
    data: {
        registerForm: {
            username: '',
            password: '',
            email:'',
            mobile:''
        },
        registerRules: {
            username: [
                {required: true, message: '请输入用户名', trigger: 'blur'},
            ],
            password: [
                {required: true, message: '请输入密码', trigger: 'blur'}
            ],
            email: [
                {required: true, message: '请输入邮箱', trigger: 'blur'}
            ]
        },
        register: false
    },
    mounted() {
    },
    methods: {
        submitForm(formName) {
            this.register = true
            var self = this;
            var form = this.$refs[formName];
            form.validate(function (valid) {
                if (!valid) {
                    self.register = false
                    return false;
                }
                Vue.http.post('./user/register',
                    {
                        username: self.registerForm.username,
                        password: self.registerForm.password,
                        email: self.registerForm.email,
                        mobile: self.registerForm.mobile
                    }
                ).then(function (res) {
                    var data = res.body
                    if (data.status === 0) {
                        self.$message({
                            message: '注册成功，请登录',
                            type:'success',
                            duration:1000,
                            onClose:function () {
                                window.location.href = "login.html"
                            }
                        })
                        self.register = false
                    } else {
                        self.$message.error(data.statusInfo)
                        self.register = false
                    }
                }, function () {
                    self.$message.error('注册异常')
                    self.register = false
                })
            })
        }
    }
});
