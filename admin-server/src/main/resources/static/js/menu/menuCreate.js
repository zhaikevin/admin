var app = new Vue({
    el: "#app",
    data: {
        createForm: {
            type:1,
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
        typeDisabled: false,
        parentName: '',
        systemList: [],
        iconList: [
            {key:'el-icon-date',label:'el-icon-date'},
            {key:'el-icon-document',label:'el-icon-document'},
            {key:'el-icon-edit-outline',label:'el-icon-edit-outline'},
            {key:'el-icon-message',label:'el-icon-message'},
            {key:'el-icon-location',label:'el-icon-location'},
            {key:'el-icon-bell',label:'el-icon-bell'},
            {key:'el-icon-setting',label:'el-icon-setting'},
            {key:'el-icon-tickets',label:'el-icon-tickets'}
        ],
        createRules: {
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
    mounted() {
        this.fetchSystem();
        this.init();
    },
    methods: {
        init() {
            var parentId = window.parent.app.checkedId
            if(parentId == 0) {
                this.createForm.type = 1
                this.typeDisabled = true
            } else {
                this.typeDisabled = false
            }
            this.createForm.parentId = parentId
            this.parentName = window.parent.app.checkedName
        },
        fetchSystem() {
            var self = this;
            Vue.http.get('../../system/getAll',
                {
                }).then(function (res) {
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

                Vue.http.post('../../menu/create',
                    {
                        type:self.createForm.type,
                        name:self.createForm.name,
                        code:self.createForm.code,
                        url:self.createForm.url,
                        parentId:self.createForm.parentId,
                        systemId:self.createForm.systemId,
                        icon:self.createForm.icon,
                        sortId:self.createForm.sortId,
                        remark:self.createForm.remark,
                        isValid:self.createForm.isValid,
                    }
                ).then(function (res) {
                    var data = res.body;
                    if (data.status === 0) {
                        window.parent.app.createDialog = false
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
