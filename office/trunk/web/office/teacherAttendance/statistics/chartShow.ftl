<#import "/common/commonmacro.ftl" as commonmacro>
<#import "/common/eisu/eisuCommonmacro.ftl" as eisuCommonmacro>
<#import "/common/flash.ftl" as flash>
<#import "/common/chartstructure.ftl" as chartstructure>
<#import "/common/htmlcomponent.ftl" as common>
<@chartstructure.histogram loadingDivId="loadingDivId1" jsonStringData=resultData isNeedAverage=false  isNeedMax=false isNeedMin=false barWidth=30 divStyle="width: 1000px;height: 400px;margin:0px auto;"/>
