"use strict";(self["webpackChunkdobby_web"]=self["webpackChunkdobby_web"]||[]).push([[106],{906:function(e,t,n){n.d(t,{Z:function(){return c}});var a=n(3396),o=n(7139);const u={class:"table-container"};function r(e,t,n,r,l,s){return(0,a.wg)(),(0,a.iD)("div",u,[(0,a._)("table",null,[(0,a._)("thead",null,[(0,a._)("tr",null,[((0,a.wg)(!0),(0,a.iD)(a.HY,null,(0,a.Ko)(n.headers,(e=>((0,a.wg)(),(0,a.iD)("th",{key:e},(0,o.zw)(e),1)))),128))])]),(0,a._)("tbody",null,[((0,a.wg)(!0),(0,a.iD)(a.HY,null,(0,a.Ko)(n.tableData,((e,t)=>((0,a.wg)(),(0,a.iD)("tr",{key:t},[((0,a.wg)(!0),(0,a.iD)(a.HY,null,(0,a.Ko)(e,((e,t)=>((0,a.wg)(),(0,a.iD)("td",{key:t},(0,o.zw)(e),1)))),128))])))),128))])])])}var l={name:"TableComponent",props:{headers:{type:Array,required:!0},tableData:{type:Array,required:!0}}},s=n(89);const i=(0,s.Z)(l,[["render",r]]);var c=i},106:function(e,t,n){n.r(t),n.d(t,{default:function(){return P}});var a=n(3396),o=n(7139),u=n(9242);const r=(0,a._)("div",{class:"content-header"},[(0,a._)("h1",null," 사원별 잔여 연차 조회 ")],-1),l={class:"content-container"},s={class:"search-input-container"},i={class:"input-group mgb-1r"},c=["value"],b=(0,a._)("option",{value:null},"월",-1),g=["value"],d={class:"input-group-append"},p={class:"paging-button-container"},y={key:1,type:"button",class:"btn btn-secondary mgb-1r mgr-1r"},m={key:3,type:"button",class:"btn btn-secondary mgb-1r"};function h(e,t,n,h,w,v){const D=(0,a.up)("TableComponent");return(0,a.wg)(),(0,a.iD)(a.HY,null,[r,(0,a._)("div",l,[(0,a._)("div",s,[(0,a._)("div",i,[(0,a.wy)((0,a._)("select",{"onUpdate:modelValue":t[0]||(t[0]=e=>w.year=e),class:"year-input"},[((0,a.wg)(!0),(0,a.iD)(a.HY,null,(0,a.Ko)(v.years,(e=>((0,a.wg)(),(0,a.iD)("option",{value:e},(0,o.zw)(e),9,c)))),256))],512),[[u.bM,w.year]]),(0,a.wy)((0,a._)("select",{"onUpdate:modelValue":t[1]||(t[1]=e=>w.month=e),class:"month-input"},[b,((0,a.wg)(!0),(0,a.iD)(a.HY,null,(0,a.Ko)(v.months,(e=>((0,a.wg)(),(0,a.iD)("option",{value:e},(0,o.zw)(e),9,g)))),256))],512),[[u.bM,w.month]]),(0,a.wy)((0,a._)("input",{type:"text",class:"form-control","onUpdate:modelValue":t[2]||(t[2]=e=>w.query=e)},null,512),[[u.nr,w.query]]),(0,a._)("div",d,[(0,a._)("button",{type:"button",class:"btn btn-outline-secondary",onClick:t[3]||(t[3]=()=>{})},"검색")])]),(0,a._)("div",p,[w.hasPreviousPage?((0,a.wg)(),(0,a.iD)("button",{key:0,type:"button",class:"btn btn-info mgb-1r mgr-1r",onClick:t[4]||(t[4]=(...e)=>v.doPreviousPage&&v.doPreviousPage(...e))},"이전")):((0,a.wg)(),(0,a.iD)("button",y,"이전")),w.hasNextPage?((0,a.wg)(),(0,a.iD)("button",{key:2,type:"button",class:"btn btn-info mgb-1r",onClick:t[5]||(t[5]=(...e)=>v.doNextPage&&v.doNextPage(...e))},"다음")):((0,a.wg)(),(0,a.iD)("button",m,"다음"))])]),(0,a.Wm)(D,{"table-data":[["M006","홍길동","매니저","2015-12-32","15","12","3"]],headers:w.columns},null,8,["headers"])])],64)}n(560);var w=n(906),v={name:"DayoffListView",components:{TableComponent:w.Z},data(){return{year:(new Date).getFullYear(),pageNum:0,pageSize:10,month:null,query:"",hasNextPage:!1,hasPreviousPage:!1,columns:["사번","이름","직급","입사일","총연차","사용연차","남은연차"]}},methods:{doNextPage(){this.pageNum++},doPreviousPage(){this.pageNum--,this.pageNum<=0&&(this.hasPreviousPage=!1)}},computed:{years(){const e=(new Date).getFullYear(),t=[];for(let n=2015;n<=e;n++)t.push(n);return t},months(){const e=[];for(let t=1;t<=12;t++)e.push(t);return e}}},D=n(89);const _=(0,D.Z)(v,[["render",h]]);var P=_}}]);
//# sourceMappingURL=106.daa03c9c.js.map