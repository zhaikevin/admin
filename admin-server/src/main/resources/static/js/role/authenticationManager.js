var app = new Vue({
    el: "#app",
    data: {
        defaultProps: {
            children: 'children',
            label: 'name'
        },
        data: [],
    },
    created() {
        this.fetchData();
    },
    mounted() {
        this.getMenuCode();
    },
    methods: {
        getMenuCode() {
            var self = this;
            Vue.http.get('../../authentication/getMenuCode',
                {
                    params:{
                        roleId:window.parent.app.authenticationId
                    }
                }
            ).then(function (res) {
                var data = res.body
                if (data.status === 0) {
                    self.$refs.tree.setCheckedKeys(data.data)
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取菜单异常')
            });
        },
        fetchData() {
            var self = this;
            Vue.http.get('../../menu/all',
                {
                    params:{
                        state:1
                    }
                }
            ).then(function (res) {
                var data = res.body
                if (data.status === 0) {
                    self.data = data.data
                } else {
                    self.$message.error(data.statusInfo)
                }
            }, function () {
                self.$message.error('获取菜单异常')
            });
        },
        save(){
            var nodes = this.$refs.tree.getCheckedNodes(false,true)
            console.log(nodes)
        }
    }
});