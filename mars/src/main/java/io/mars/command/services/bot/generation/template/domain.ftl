intents:
<#list commands  as command>
  - ${command.commandName}
</#list>

actions:
- action_call_command

slots:
<#list commands  as command>
   <#list command.params as param>
   ${command.commandNameSnakeCase}_param_${param?index}:
      type: unfeaturized
   </#list>
</#list>