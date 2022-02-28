package rk.shell.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.Utils;
import org.springframework.stereotype.Component;
import rk.shell.example.command.CustomScript;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Aspect
@Component
public class CommandAspect {
    private static final Logger log = LoggerFactory.getLogger("FEEDBACK");
    @Around("within(rk.shell.example.command..*)")
    public Object allCommands(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String commandName = Utils.unCamelify(signature.getMethod().getName());
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        String parameters = "";
        if(parameterNames.length == args.length){
            parameters = IntStream.range(0, parameterNames.length)
                    .mapToObj(i -> String.format("--%s %s", Utils.unCamelify(parameterNames[i]), args[i]))
                    .collect(Collectors.joining(" "));
        }
        log.info("Executing command [{} {}]", commandName, parameters);
        try{
            return joinPoint.proceed();
        }catch(RuntimeException e){
            log.error("Command [{} {}] failed", commandName,parameters, e);
            if(CustomScript.EXIT_ON_ERROR.get()){
                CustomScript.EXIT_ON_ERROR.set(false);
                throw new ExitRequest();
            }
            throw e;
        }
        
    }
}
