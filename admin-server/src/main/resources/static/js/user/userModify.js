var app = new Vue({
    el: "#app",
    data: {
        modifyForm: {
            id:0,
            username: '',
            email: '',
            mobile: '',
            state:1,
        },
        modifyRules: {
            state: [
                {required: true, message: '请选择状态', trigger: 'change'}
            ],
            username: [
                {required: true, message: '请输入用户名', trigger: 'blur'}
            ],
            email: [
                {required: true, message: '请输入邮箱', trigger: 'blur'}
            ]
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
                            id: window.parent.app.modifyId
                        }
                }
            ).then(function (res) {
                var data = res.body;
                if (data.status === 0) {
                    self.modifyForm.id = data.data.id
                    self.modifyForm.username = data.data.username
                    self.modifyForm.email = data.data.email
                    self.modifyForm.mobile = data.data.mobile
                    self.modifyForm.state = data.data.state
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
                Vue.http.post('../../user/modify',
                    {
                        id: self.modifyForm.id,
                        username: self.modifyForm.username,
                        email: self.modifyForm.email,
                        mobile: self.modifyForm.mobile,
                        state: self.modifyForm.state
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
                    self.$message.error('编辑用户异常')
                })
            })
        },
        cancel() {
            window.parent.app.modifyDialog = false
        }
    }
});
