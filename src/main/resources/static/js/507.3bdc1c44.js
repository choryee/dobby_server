"use strict";(self["webpackChunkdobby_web"]=self["webpackChunkdobby_web"]||[]).push([[507],{5507:function(e,r,n){n.r(r),n.d(r,{default:function(){return p}});var t=n(3396),a=n(7139),o=n(4870),l=n(6680),d={auth:{login(e,r){return l.Z.POST("login",e,r)},adminPasswordChange(e,r){return l.Z.PATCH("auth/password")}},employee:{employeeInsert(e,r){return l.Z.POST("employee/insert",e,r)},employeeList(e,r){return l.Z.GET("employee/list",e,r)},employeeDelete(e,r){return l.Z.DELETE("employee/delete/",e,r)},employeeUpdate(e,r){return l.Z.PUT("employee/update/",e,r)}},dayoff:{dayoffObligation(e,r){return l.Z.POST("dayoff/obligation/",e,r)},dayoffWorkSave(e,r){return l.Z.POST("dayoff/work/save",e,r)},dayoffWorkInfo(e,r){return l.Z.GET("dayoff/work/info",e,r)},dayoffWorkDelete(e,r){return l.Z.DELETE("dayoff/work/delete",e,r)},dayoffUse(e,r){return l.Z.GET("dayoff/employee",e,r)},dayoffRemaining(e,r){return l.Z.GET("dayoff/employee/remaining",e,r)}},calender:{calenderPatternInsert(e,r){return l.Z.POST("calender/pattern/Insert",e,r)},calenderPatternInfo(e,r){return l.Z.GET("calender/pattern/info",e,r)},calenderPatternDelete(e,r){return l.Z.DELETE("/calender/pattern/delete",e,r)}}},f={__name:"NetworkEx",setup(e){const r=(0,o.iH)("");return(0,t.bv)((()=>{d.auth.login().then((e=>{r.value=e})).catch((e=>{r.value=e}))})),(e,n)=>((0,t.wg)(),(0,t.iD)("pre",null,"    "+(0,a.zw)(r.value)+"\n  ",1))}},u=n(89);const y=(0,u.Z)(f,[["__scopeId","data-v-0d07e216"]]);var p=y}}]);
//# sourceMappingURL=507.3bdc1c44.js.map