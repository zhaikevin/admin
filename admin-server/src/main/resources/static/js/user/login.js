var app = new Vue({
    el: "#app",
    data: {
        loginForm: {
            username:'',
            password: '',
        },
        loginRules: {
            username: [
                {required: true, message: '请输入用户名', trigger: 'blur'},
            ],
            password: [
                {required: true, message: '请输入密码', trigger: 'blur'}
            ]
        },
        login: false
    },
    mounted() {
    },
    methods: {
        submitForm(formName) {
            var self = this;
            var form = this.$refs[formName];
            form.validate(function (valid) {
                if (!valid) {
                    return false;
                }
                Vue.http.post('./user/login',
                    {
                        username: self.loginForm.username,
                        password: self.loginForm.password
                    }
                ).then(function (res) {
                    var data = res.body;
                    if (data.status === 0) {
                        self.$message.success("欢迎回来")
                        window.location.href="index.html"
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('登录异常');
                });
            })
        }
    }
});
