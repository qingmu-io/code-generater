package ${basePackage}.mapper;


import ${mmm.modelName};
<#if mmm.simpleQuery != ''>
import java.util.List;
import ${mmm.queryModel};
</#if>
public interface ${mmm.simpleName}Mapper {

	int insert(${mmm.simpleName} ${mmm.simpleName?uncap_first});

	int update(${mmm.simpleName} ${mmm.simpleName?uncap_first});

	int merge(${mmm.simpleName} ${mmm.simpleName?uncap_first});

	int delete(Long id);

	${mmm.simpleName} findOne(Long id);
	<#if mmm.simpleQuery != ''>
	
	List<${mmm.simpleName}> findAll(${mmm.simpleQuery} ${mmm.simpleQuery?uncap_first});

	long count(${mmm.simpleQuery} ${mmm.simpleQuery?uncap_first});
	</#if>
}