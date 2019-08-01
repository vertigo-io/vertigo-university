<#list commands  as command>
## intent:${command.commandName}
<#list command.examples as example>
- ${example}
</#list>

</#list>