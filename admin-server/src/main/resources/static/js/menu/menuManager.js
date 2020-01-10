var app = new Vue({
    el: "#app",
    data: {
        defaultProps: {
            children: 'children',
            label: 'name'
        },
        data: [],
        createDialog: false,
        createUrl: '',
        checkedId: 0,
        checkedName: '',
        createDisabled: false
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        fetchData() {
            var self = this;
            Vue.http.get('../../menu/all',
                {

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
        handleClick(data,checked, node){
            if(checked){
                this.$refs.tree.setCheckedNodes([data])
            }
            var nodes = this.$refs.tree.getCheckedNodes()
            if(nodes.length > 0) {
                var node = nodes[0]
                this.checkedId = node.id
                this.checkedName = node.name
                if(node.type === 2) {
                    this.createDisabled = true
                } else {
                    this.createDisabled = false
                }
            } else {
                this.checkedId = 0
                this.checkedName = ''
                this.createDisabled = false
            }
        },
        beforeCreateCloseDialog(done) {
            this.createUrl = ''
        },
        showCreateDialog: function () {
            this.createDialog = true;
            this.createUrl = 'menuCreate.html?new=' + Math.random();
        },
    }
});