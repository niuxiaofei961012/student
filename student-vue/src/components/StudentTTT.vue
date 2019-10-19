<template>
<el-main>
  <el-form :inline="true" :model="queryForm" class="demo-form-inline">
    <el-form-item label="姓名">
      <el-input v-model="queryForm.name" clearable placeholder="姓名"></el-input>
    </el-form-item>
    <el-form-item label="年龄">
      <el-input v-model="queryForm.ageStart" clearable placeholder="开始"></el-input>
    </el-form-item>
    <el-form-item label="到">
      <el-input v-model="queryForm.ageEnd" clearable placeholder="结束"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
    </el-form-item>
  </el-form>
  <el-table
    ref="multipleTable"
    :data="tableData"
    tooltip-effect="dark"
    style="width: 100%"
    @selection-change="handleSelectionChange">
    <el-table-column
      type="selection"
      width="55">
    </el-table-column>
    <el-table-column
      label="#"
      prop="id"
      width="60">

    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名"
      width="80">
    </el-table-column>
    <el-table-column
      label="头像"
      width="80">
      <template slot-scope="scope">
        <img :src="scope.row.header_img" alt="" style="height: 60px;">
      </template>
    </el-table-column>
    <el-table-column
      prop="age"
      label="年龄"
      width="80"
      show-overflow-tooltip>
    </el-table-column>
    <el-table-column
      prop="sex"
      label="性别"
      width="80"
      show-overflow-tooltip>
      <template slot-scope="scope">
        <span v-if="scope.row.sex==1">男</span>
        <span v-else-if="scope.row.sex==2">女</span>
        <span v-else="scope.row.sex==0">未知</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="classes_name"
      width="80"
      label="班级"
      show-overflow-tooltip>
    </el-table-column>

    <el-table-column
      prop="hoddyNames"
      width="80"
      label="爱好"
      show-overflow-tooltip>
    </el-table-column>

    <el-table-column
      label="地区"
      width="150"
      show-overflow-tooltip>
      <template slot-scope="scope">
        {{scope.row.provice_name}}/{{scope.row.city_name}}/{{scope.row.area_name}}
      </template>
    </el-table-column>

    <!--<el-table-column
      prop="city_name"
      label="市"
      show-overflow-tooltip>
    </el-table-column>

    <el-table-column
      prop="area_name"
      label="区"
      show-overflow-tooltip>
    </el-table-column>-->

    <!--<el-table-column
      prop="create_time"
      label="日期"
      show-overflow-tooltip>
    </el-table-column>-->
    <el-table-column
      label="操作"
      width="150"
      show-overflow-tooltip>
      <template slot-scope="scope">
        <el-button
        @click="edit(scope.row.id)"
        type="text"
        size="small">
        编辑
      </el-button>
       <!-- <el-button
          @click="del(scope.row.id)"
          type="text"
          size="small">
          删除
        </el-button>-->
        <el-button type="text" @click="open(scope.row.id)">删除</el-button>
        <el-button
          @click="detail(scope.row.id)"
          type="text"
          size="small">
          详情
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
    :page-count="pages"
    @current-change="goToPage"
    layout="prev, pager, next">
  </el-pagination>
  <div style="margin-top: 20px">
    <el-button @click="batchDel">批量删除</el-button>
    <el-button @click="add()">添加</el-button>
  </div>

  <el-dialog
    title="提示"
    :visible.sync="dialogVisible"
    width="60%"
    >
      <el-form :inline="true" :model="saveForm" class="demo-form-inline">
        <el-form-item label="姓名">
          <el-input v-model="saveForm.name" placeholder="请输入姓名"></el-input>
        </el-form-item><br>

        <el-form-item label="爱好">
          <el-checkbox-group v-model="hoddyIds">
            <el-checkbox :label="hobby.id" :key="hobby.id" v-for="hobby in hoddyList">{{hobby.name}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item><br>


        <el-form-item label="头像">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:90/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="saveForm.header_img" :src="saveForm.header_img" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item><br>
        <el-form-item label="性别">
          <el-radio v-model="saveForm.sex" label="0">未知</el-radio>
          <el-radio v-model="saveForm.sex" label="1">男</el-radio>
          <el-radio v-model="saveForm.sex" label="2">女</el-radio>
        </el-form-item><br>
        <el-form-item label="年龄">
          <el-input v-model="saveForm.age" placeholder="请输入年龄"></el-input>
        </el-form-item><br>
        <el-form-item label="班级">
          <el-select v-model="saveForm.classes_id" placeholder="请选择班级">
            <el-option
              v-for="item in classesList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item><br>

        <el-form-item label="省">
          <el-select v-model="saveForm.provice_id" @change="changeCity" placeholder="请选择省">
            <el-option
              v-for="item in proviceList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item><br>

        <el-form-item label="市">
          <el-select v-model="saveForm.city_id" @change="changeArea" placeholder="请选择市">
            <el-option
              v-for="item in cityList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item><br>

        <el-form-item label="县">
          <el-select v-model="saveForm.area_id" placeholder="请选择县">
            <el-option
              v-for="item in areaList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item><br>

        <el-form-item label="日期">
          <el-date-picker
            v-model="saveForm.birthday"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出生日期">
          </el-date-picker>
        </el-form-item><br>
        <el-form-item label="说明">
          <el-input
            type="textarea"
            :rows="3"
            placeholder="请输入内容"
            v-model="saveForm.introduction">
          </el-input>
        </el-form-item>
      </el-form>
    <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
  </el-dialog>

</el-main>
</template>

<script>
    const axios = require("axios");
    const baseUrl = "http://localhost:90";
    export default {
        name: "Student",
      data(){
          return {
            tableData: [],
            queryForm:{
              pageNo:1,
              pageSize:3
            },
            classesList: [],
            proviceList:[{id:1,name:"河北"}],
            cityList:[],
            areaList:[],
            saveForm:{header_img:""},
            pages:0,
            ids:null,
            dialogVisible:false,
            fileName:null,
            hoddyList:[{id:1,name:"爱好1"},{id:2,name:"爱好2"},{id:3,name:"爱好3"}],
            hoddyIds: []
          }
      },
      methods:{
        handleAvatarSuccess(res, file) {
          console.log(res.imgUrl)
          this.saveForm.header_img = res.imgUrl;
          let fileName = this.fileName;
          if(fileName!=null){
            axios({
              url:baseUrl+"/file/del",
              method:"get",
              params:{fileName:fileName}
            }).then(function (res) {
              if(res.data.result){
                console.log("文件已删除")
              }else{
                console.log("文件删除失败")
              }
            })
          }
          this.fileName = res.fileName;
        },
        beforeAvatarUpload(file) {
          const isJPG = file.type === 'image/jpeg';
          const isLt2M = file.size / 1024 / 1024 < 2;

          if (!isJPG) {
            this.$message.error('上传头像图片只能是 JPG 格式!');
          }
          if (!isLt2M) {
            this.$message.error('上传头像图片大小不能超过 2MB!');
          }
          return isJPG && isLt2M;
        },
        getList(){
          let self = this;
          axios({
            url:baseUrl+"/student/getPageInfo",
            method:"get",
            params:this.queryForm
          }).then(function (res) {
            console.log(res.data.list);
            self.tableData=res.data.list;
            self.pages = res.data.pages;
          })
        },
        goToPage(pageNo){
          console.log(pageNo);
          this.queryForm.pageNo = pageNo;
          this.getList();
        },
        batchDel(){
            if(this.ids==null){
              this.$message("请选择要删除的记录");
              return;
            }
            let self =  this;
            axios({
              url:baseUrl+"/student/deleteByIds",
              method:"get",
              params:{ids:this.ids}
            }).then(function (res) {
              if(res.data){
                self.queryForm.pageNo=1;
                self.getList();
              }
            })
        },
        add(){
          this.dialogVisible = true;
          this.cityList=[];
          this.areaList=[];
          this.hoddyIds=[];
          this.saveForm={header_img:""};
        },
        edit(id){
          this.dialogVisible=true;
          let self = this;
          axios({
            url:baseUrl+"/student/getById",
            method:"get",
            params:{id:id}
          }).then(function (res) {
            if(res.data){
              self.cityList = res.data.cityList;
              self.areaList = res.data.areaList;
              self.saveForm = res.data.student;
              self.saveForm.sex = res.data.student.sex+"";
              self.fileName = res.data.student.fileName;
              self.hoddyIds = res.data.student.hoddyIds;
            }
          })
        },
        save(){
          let saveForm = this.saveForm;
          if(saveForm.name==null){
            this.$message("请输入姓名");
            return;
          }
          if(saveForm.age==null){
            this.$message("请输入年龄");
            return;
          }
          let self =  this;
          console.log(saveForm)
          saveForm.hoddyIds = this.hoddyIds;
          console.log(this.hobbyIds);
          // return;
          axios({
            url:baseUrl+"/student/save",
            method:"post",
            data:saveForm
          }).then(function (res) {
            if(res.data){
              self.dialogVisible=false;
              self.getList();
            }
          })
        }/*,
        del(id){
          let self = this;
          axios({
            url:baseUrl+"/student/deleteByIds",
            method:"get",
            params:{ids:id}
          }).then(function (res) {
              if(res.data){
                self.queryForm.pageNo=1;
                self.getList();
              }
          })
        }*/,
        detail(id){
          this.$router.push("/detail/"+id);
        },
        onSubmit(){
            console.log(this.queryForm);
            // this.queryForm.pageNo=1;
            this.getList();
        },
        handleSelectionChange(rows) {
          let ids = null;
          rows.forEach(row=>{
            if(ids==null){
              ids=row.id;
            }else{
              ids=ids+","+row.id;
            }
          })
          this.ids = ids;
          console.log(ids);
        },
        open(id) {
          let self = this;
          this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            axios({
              url:baseUrl+"/student/deleteByIds",
              method:"get",
              params:{ids:id}
            }).then(function (res) {
              if(res.data){
                self.$message({
                  type: 'success',
                  message: '删除成功!'
                });
                self.getList();
              }else{
                self.$message("删除失败");
              }
            })

          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        },
        changeCity(){
          let self = this;
          let pid = this.saveForm.provice_id;
          axios({
            url:baseUrl+"/student/getAreaListById",
            method:"get",
            params:{pid:pid}
          }).then(function (res) {
            console.log(res)
            self.cityList=res.data;
          })
        },
        changeArea(){
          let self = this;
          let pid = this.saveForm.city_id;
          axios({
            url:baseUrl+"/student/getAreaListById",
            method:"get",
            params:{pid:pid}
          }).then(function (res) {
            console.log(res)
            self.areaList=res.data;
          })
        },
      },
      created(){
        this.getList();
        let self = this;
        axios({
          url:baseUrl+"/student/initData",
          method:"get"
        }).then(function (res) {
            self.classesList=res.data.classesList;
            self.proviceList = res.data.priviceList;
            self.hoddyList = res.data.hoddyList;
            self.getList();

        })
      }
    }
</script>

<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
