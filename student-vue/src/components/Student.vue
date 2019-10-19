<template>
  <el-main>
    <h1>学生操作系统</h1>
    <el-form :inline="true" :model="queryForm" class="demo-form-inline">
      <el-form-item label="按地区">
        <el-radio-group v-model="queryForm.areas" size="mini"  @change="area">
            <el-radio-button :label="item.id" :key="item.id" v-for="item in proviceList" >
              {{item.name}}
            </el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="按价格">
        <el-radio-group v-model="queryForm.price" size="mini"  @change="price">
          <el-radio-button :label="item" :key="item" v-for="item in priceList" >
              <span v-if="item==1" >600-1000</span>
              <span v-else-if="item==2">1000-1500</span>
              <span v-else-if="item==3">1500-2000</span>
              <span v-else-if="item==4">2000-2500</span>
              <span v-else-if="item==5">2500-3000</span>
              <span v-else-if="item==6">3000-4000</span>
          </el-radio-button>
        </el-radio-group>
      </el-form-item>
      <br/>
      <el-form-item label="姓名">
        <el-input v-model="queryForm.name" placeholder="姓名"></el-input>
      </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model="queryForm.age" placeholder="年龄"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="..."
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        prop="id"
        label="编号"
        width="120">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="120">
      </el-table-column>
      <el-table-column
        prop="age"
        label="年龄"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别"
        show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.sex==1">男</span>
          <span v-else-if="scope.row.sex==2">女</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="classes_name"
        label="班级"
        show-overflow-tooltip>
      </el-table-column>

      <el-table-column
        prop="provice_name"
        label="省"
        show-overflow-tooltip>
      </el-table-column>

      <el-table-column
        prop="city_name"
        label="市"
        show-overflow-tooltip>
      </el-table-column>

      <el-table-column
        prop="area_name"
        label="区"
        show-overflow-tooltip>
      </el-table-column>

      <el-table-column
        prop="birthday"
        label="出生日期"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="introduction"
        label="说明"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="create_time"
        label="创建日期"
        value-format="yyyy-MM-dd"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        label="操作"
        show-overflow-tooltip>
        <template slot-scope="scope">
          <el-button @click="update(scope.row.id)" type="text" size="small">
            编辑
          </el-button>
          <el-button type="text" @click="open(scope.row.id)">删除</el-button>
          <el-button @click="detail()" type="text" size="small">
            详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      :page-count="pages"
      @current-change="goToPage"
      layout="prev, pager, next">
    </el-pagination>
    <div style="margin-top: 20px">
      <el-button @click="add()">添加</el-button>
      <el-button @click="delBatch()">批量删除</el-button>
    </div>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="60%">
      <el-form :inline="true" :model="saveForm" class="demo-form-inline">
        <el-form-item label="姓名">
          <el-input v-model="saveForm.name" placeholder="姓名"></el-input>
        </el-form-item>
        <br/>
        <el-form-item label="头像">
          <el-upload
            class="avatar-uploader"
            action="http://127.0.0.1:90/student/upLoad"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="saveForm.imageUrl" :src="saveForm.imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <br/>
        <el-form-item label="年龄">
          <el-input v-model="saveForm.age" placeholder="年龄"></el-input>
        </el-form-item>
        <br/>
        <el-form-item label="性别">
          <el-radio v-model="saveForm.sex" label="1">男</el-radio>
          <el-radio v-model="saveForm.sex" label="2">女</el-radio>
        </el-form-item>
        <br/>
        <el-form-item label="出生日期">
          <el-date-picker
            v-model="saveForm.birthday"
            align="right"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <br/>
        <el-form-item label="班级">
          <el-select v-model="saveForm.classes_id" placeholder="请选择班级">
            <el-option
              v-for="item in classesList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <br/>
        <el-form-item label="省">
          <el-select v-model="saveForm.provice_id" placeholder="请选择省" @change="changeCity">
            <el-option
              v-for="item in proviceList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <br/>
        <el-form-item label="市">
          <el-select v-model="saveForm.city_id" placeholder="请选择市" @change="changeArea">
            <el-option
              v-for="item in cityList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <br/>
        <el-form-item label="区">
          <el-select v-model="saveForm.area_id" placeholder="请选择区">
            <el-option
              v-for="item in areaList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <br/>
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
    data() {
      return {
        tableData: [],
        multipleSelection: [],
        queryForm: {
          pageNo: 1,
          pageSize: 3
        },
        saveForm: {},
        pages: 0,
        dialogVisible: false,
        ids: null,
        proviceList: [],
        cityList: [],
        areaList: [],
        classesList: [],
        priceList: [1,2,3,4,5,6]
      }
    },

    methods: {
      price(id){
        console.log(id)
      },
      area(id) {
        console.log(id)
      },
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      add() {
        this.saveForm = {area_id: null, imageUrl: ""};
        this.dialogVisible = true;
      },
      save() {
        let self = this;
        axios({
          url: baseUrl + "/student/save",
          method: "post",
          data: self.saveForm
        }).then(function (res) {
          if (res.data) {
            self.dialogVisible = false;
            self.getList();
          }
        })
      },
      open(id) {
        let self = this;
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios({
            url: baseUrl + "/student/deleteByIds",
            method: "get",
            params: {ids: id},
          }).then(function (res) {
            if (res) {
              self.$message({
                type: 'success',
                message: '删除成功!'
              })
              self.queryForm.pageNo = 1;
              self.getList();
            } else {
              self.$message("删除失败")
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      delBatch() {
        let self = this;
        let ids = this.ids;
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios({
            url: baseUrl + "/student/deleteByIds",
            method: "get",
            params: {ids: ids},
          }).then(function (res) {
            if (res) {
              self.$message({
                type: 'success',
                message: '删除成功!'
              })
              self.queryForm.pageNo = 1;
              self.getList();

            } else {
              self.$message("删除失败")
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      update(id) {
        this.dialogVisible = true;
        let self = this;
        axios({
          url: baseUrl + "/student/getById",
          method: "get",
          params: {id: id},
        }).then(function (res) {
          if (res.data) {
            self.saveForm = res.data.students;
            self.cityList = res.data.cityList;
            self.areaList = res.data.areaList;
            self.saveForm.sex = res.data.students.sex + "";
          }
        })
      },
      detail() {

      },
      handleSelectionChange(rows) {
        let ids = null;
        rows.forEach(row => {
          if (ids == null) {
            ids = row.id;
          } else {
            ids = ids + "," + row.id
          }
        })
        this.ids = ids;
      },
      onSubmit() {
        this.getList();
      },
      goToPage(pageNo) {
        this.queryForm.pageNo = pageNo;
        this.getList();
      },
      getList() {
        let self = this;
        axios({
          url: baseUrl + "/student/getPageInfo",
          method: "get",
          params: this.queryForm,
        }).then(function (res) {
          self.tableData = res.data.list;
          self.pages = res.data.pages;
        })
      },
      changeCity() {
        this.saveForm.area_id = "";
        this.saveForm.city_id = "";
        this.cityList = [];
        this.areaList = [];
        let self = this;
        let id = this.saveForm.provice_id;
        axios({
          url: baseUrl + "/student/getAreas",
          method: "get",
          params: {pid: id}
        }).then(function (reg) {
          self.cityList = reg.data;
        })
      },
      changeArea() {
        this.saveForm.area_id = "";
        this.areaList = [];
        let self = this;
        let id = this.saveForm.city_id;
        axios({
          url: baseUrl + "/student/getAreas",
          method: "get",
          params: {pid: id}
        }).then(function (reg) {
          self.areaList = reg.data;
        })
      },
      handleAvatarSuccess(res, file) {
        this.saveForm.imageUrl = res.imageUrl;
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
      }
    },
    created() {
      this.getList();
      let self = this;
      axios({
        url: baseUrl + "/student/initData",
        method: "get",
      }).then(function (res) {
        self.classesList = res.data.classesList;
        self.proviceList = res.data.proviceList;
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
