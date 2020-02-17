var app = new Vue({
    el: "#app",
    data: {
        infoForm: {
            id: 0,
            username: '',
            email: '',
            mobile: '',
            group: '',
        },
        infoRules: {
            email: [
                {required: true, message: '请输入邮箱', trigger: 'blur'}
            ]
        }
    },
    mounted() {
        this.getGroup()
        this.fetchUserInfo()
    },
    methods: {
        getGroup() {
            var groupList = JSON.parse(Cookies.get('user_group'))
            if (groupList.length === 0) {
                this.infoForm.group = '无'
            } else {
                var groupName=''
                for (var i = 0; i < groupList.length; i++) {
                    var group = groupList[i]
                    groupName = groupName + group.groupName + ','
                }
                this.infoForm.group = groupName.substr(0, groupName.length - 1)
            }
        },
        fetchUserInfo() {
            var self = this;
            Vue.http.get('../../user/getById',
                {
                    params:
                        {
                            id: Cookies.get('user_id')
                        }
                }
            ).then(function (res) {
                var data = res.body;
                if (data.status === 0) {
                    self.infoForm.id = data.data.id
                    self.infoForm.username = data.data.username
                    self.infoForm.email = data.data.email
                    self.infoForm.mobile = data.data.mobile
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取用户信息异常');
            });
        },
        submitForm(formName) {
            var self = this;
            var form = this.$refs[formName];
            form.validate(function (valid) {
                if (!valid) {
                    return false;
                }
                Vue.http.post('../../user/modify',
                    {
                        id: self.infoForm.id,
                        email: self.infoForm.email,
                        mobile: self.infoForm.mobile,
                    }
                ).then(function (res) {
                    var data = res.body
                    if (data.status === 0) {
                        window.parent.app.infoDialog = false
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('编辑用户异常')
                })
            })
        },
        cancel() {
            window.parent.app.infoDialog = false
        }
    }
});
