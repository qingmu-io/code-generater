<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mxm.mapperName}" >
<#assign rep="#">
<#assign rep$="$">
<#assign baseColumn><#list mxm.mappingMetas as br><#if br.column != 'id'><#if br_has_next>${br.column},<#else>${br.column}</#if></#if></#list></#assign><#assign baseProperty><#list mxm.mappingMetas as br>
<#if br.property != 'id'>
<#if br_has_next><#if br.enumHander != ''>${rep}{${br.property},typeHandler=${br.enumHander}},<#else>${rep}{${br.property}},</#if><#else><#if br.enumHander != ''>${rep}{${br.property},typeHandler=${br.enumHander}}<#else>${rep}{${br.property}}</#if></#if></#if></#list></#assign>

<resultMap id="baseResultMap" type="${mxm.model}" >
  <#list mxm.mappingMetas as br>
  		<#if br.column == 'id'>
  		<id column="${br.column}" property="${br.property}" />
  		<#else>
  		<#if br.enumHander != ''>
  		 <result column="${br.column}" property="${br.property}" typeHandler="${br.enumHander}"/>
  		<#else>
 	  	 <result column="${br.column}" property="${br.property}" />
  		</#if>
  		</#if>
  </#list>
</resultMap>
  
  
  
<insert id="insert" parameterType="${mxm.model}" useGeneratedKeys="true" keyProperty="id" >
  insert into `${mxm.table?trim}` (${baseColumn})
  values (${baseProperty})
</insert>
  
<delete id="delete">
    delete from `${mxm.table?trim}` where id = ${rep}{id}
</delete>
  
  
<update id="update" parameterType="${mxm.model}" >
    update `${mxm.table?trim}`
    <set >
<#list mxm.mappingMetas as br>
  <#if br_has_next>
 	 	<#if br.enumHander != ''>
			${br.column} = ${rep}{${br.property},typeHandler=${br.enumHander}},
 	 	<#else>
    	  ${br.column} = ${rep}{${br.property}},
 	 	</#if>
     <#else>
     	 <#if br.enumHander != ''>
			${br.column} = ${rep}{${br.property},typeHandler=${br.enumHander}}
 	 	<#else>
    	  	${br.column} = ${rep}{${br.property}}
 	 	</#if>
     </#if>
  </#list>
    </set>
    where id = ${rep}{id}
 </update>
  
  
<update id="merge" parameterType="${mxm.model}" >
    update `${mxm.table?trim}`
<set>
<#list mxm.mappingMetas as br>
  <#if br_has_next>
 	 <if test="${br.property} != null" >
    	  ${br.column} = ${rep}{${br.property}},
     </if>
    <#else>
     <if test="${br.property} != null" >
     	  ${br.column} = ${rep}{${br.property}}
     </if>
     </#if>
  </#list>
    </set>
    where id = ${rep}{id}
  </update>
 
<select id="findOne" resultMap="baseResultMap" parameterType="long">
	select  
		id,${baseColumn}
	from
		`${mxm.table?trim}`
	where id = ${rep}{id}	
</select>
  
<#if mxm.querys?size gt 0>  
<select id="findAll" resultMap="baseResultMap" parameterType="${mxm.query}" >
    select 
   	id,${baseColumn}
    from `user`
   	<where>
   		<#list mxm.querys?keys as key>
   		<if test="${key} != null">
			${mxm.querys[key]}
		</if>
   		</#list>
	</where>
	<choose>
		<when test="orderByAndDirection != null">
			order by ${rep$}{orderByAndDirection}
		</when>
		<otherwise>
			order by id desc
		</otherwise> 
	</choose>
	<if test="offset != null">
		limit ${rep}{offset}, ${rep}{pageSize}
	</if>
  </select>
  
  <select id="count" resultType="_long" parameterType="${mxm.query}" >
    select 
    count(*)
    from `user`
   	<where>
		<#list mxm.querys?keys as key>
   			<if test="${key} != null">
			${mxm.querys[key]}
		</if>
   		</#list>
	</where>
  </select>
</#if>
    
</mapper>