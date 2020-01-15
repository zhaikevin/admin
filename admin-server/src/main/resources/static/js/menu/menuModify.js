var app = new Vue({
    el: "#app",
    data: {
        modifyForm: {
            id:0,
            type: 1,
            name: '',
            code: '',
            url: '',
            parentId: '',
            systemId: '',
            icon: 'el-icon-document',
            sortId: 1,
            remark: '',
            isValid:1
        },
        menuList:[],
        systemList: [],
        iconList: [
            {key: 'el-icon-date', label: 'el-icon-date'},
            {key: 'el-icon-document', label: 'el-icon-document'},
            {key: 'el-icon-edit-outline', label: 'el-icon-edit-outline'},
            {key: 'el-icon-message', label: 'el-icon-message'},
            {key: 'el-icon-location', label: 'el-icon-location'},
            {key: 'el-icon-bell', label: 'el-icon-bell'},
            {key: 'el-icon-setting', label: 'el-icon-setting'},
            {key: 'el-icon-tickets', label: 'el-icon-tickets'}
        ],
        modifyRules: {
            type: [
                {required: true, message: '请选择类型', trigger: 'change'}
            ],
            isValid: [
                {required: true, message: '请选择状态', trigger: 'change'}
            ],
            name: [
                {required: true, message: '请输入菜单名称', trigger: 'blur'}
            ],
            code: [
                {required: true, message: '请输入菜单编码', trigger: 'blur'}
            ],
            systemId: [
                {required: true, message: '请选择所属系统', trigger: 'change'}
            ],
            remark: [
                {max: 500, message: '最大长度为255', trigger: 'blur'}
            ]
        }
    },
    created() {
        this.fetchParentMenuList();
        this.fetchSystem();
    },
    mounted() {
        this.fetchMenuInfo();
    },
    methods: {
        fetchMenuInfo() {
            var self = this;
            Vue.http.get('../../menu/getById',
                {
                    params:
                        {
                            menuId: window.parent.app.checkedId
                        }
                }
            ).then(function (res) {
                var data = res.body;
                if (data.status === 0) {
                    self.modifyForm.id = data.data.id
                    self.modifyForm.type = data.data.type
                    self.modifyForm.name = data.data.name
                    self.modifyForm.code = data.data.code
                    self.modifyForm.url = data.data.url
                    self.modifyForm.parentId = data.data.parentId
                    self.modifyForm.systemId = data.data.systemId
                    self.modifyForm.icon = data.data.icon
                    self.modifyForm.sortId = data.data.sortId
                    self.modifyForm.remark = data.data.remark
                    self.modifyForm.isValid = data.data.isValid
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取菜单异常');
            });
        },
        fetchParentMenuList() {
            var self = this;
            Vue.http.get('../../menu/getAllValidMenu',
                {
                }
            ).then(function (res) {
                var data = res.body;
                if (data.status === 0) {
                    self.menuList = data.data
                    self.menuList.unshift(
                        {
                            'id': 0,
                            'name': '根目录'
                        }
                    )
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取菜单异常');
            });
        },
        fetchSystem() {
            var self = this;
            Vue.http.get('../../system/getAll',
                {}).then(function (res) {
                var data = res.body;
                if (data.status === 0) {
                    self.systemList = data.data
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取系统列表信息异常')
            });
        },
        submitForm(formName) {
            var self = this;
            var form = this.$refs[formName];
            form.validate(function (valid) {
                if (!valid) {
                    return false;
                }

                Vue.http.post('../../menu/modify',
                    {
                        id: self.modifyForm.id,
                        type: self.modifyForm.type,
                        name: self.modifyForm.name,
                        code: self.modifyForm.code,
                        url: self.modifyForm.url,
                        parentId: self.modifyForm.parentId,
                        systemId: self.modifyForm.systemId,
                        icon: self.modifyForm.icon,
                        sortId: self.modifyForm.sortId,
                        remark: self.modifyForm.remark,
                        isValid: self.modifyForm.isValid,
                    }
                ).then(function (res) {
                    var data = res.body;
                    if (data.status === 0) {
                        window.parent.app.modifyDialog = false
                        window.parent.app.fetchData()
                    } else {
                        self.$message.error(data.statusInfo)
                    }
                }, function () {
                    self.$message.error('创建菜单异常');
                });
            })
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    }
});
