const express = require('express');
const router = express.Router();
const qs = require("qs");
const searchService  = require('./search.service')
 
 
router.get('/', search);
 
function search(req, res, next){
    let params = qs.parse(req.query);
    let type = params.type;//查询类别
    let keyword = params.keyword;//查询关键字
    let maxvalue = parseFloat(params.ceil);//查询范围上限，属性为时间
    let minvalue = parseFloat(params.floor);//查询范围下限
    let sortype = parseInt(params.sortype);//排序方式
    let sort = parseInt(params.sort);//搜索结果是否按时间进行排序
    console.log(`${type} ${keyword} ${maxvalue} ${minvalue} ${sort}`);
    switch(type){
        case 'user':
            searchService.sear_user(keyword,maxvalue,minvalue,sort,sortype).then((data)=>{
                res.json({status:0,msg:'successful to find set',result:data});
            }).catch((err)=>{
                res.json({status:1,msg:err,result:''});
            })
            break;
        case 'course':
            searchService.sear_title(keyword,maxvalue,minvalue,sort,sortype).then((data)=>{
                console.log(data);
                res.json({status:0,msg:'successful to find set',result:data});
            }).catch((err)=>{
                res.json({status:1,msg:err,result:''});
            })
            break;
        case 'label':
            searchService.sear_label(keyword,maxvalue,minvalue,sort,sortype).then((data)=>{
                console.log(data);
                res.json({status:0,msg:'successful to find set',result:data});
            }).catch((err)=>{
                res.json({status:1,msg:err,result:''});
            })
            break;
        default:(function(){
            res.json({status:'1',msg:'please choose search type!',result:''});
        })();
    }
}
