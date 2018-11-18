package org.gsm.demos.rest.aspect;

import java.lang.reflect.Method;
import java.util.StringJoiner;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * The Class LoggingAspect.
 *
 * @author aub001es
 */
@Aspect
@Component
public class LoggingAspect {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

	/** The Constant POINTCUT_EXPR. */
	private static final String POINTCUT_EXPR = "execution(* org.gsm.demos.rest..*(..))";



	/**
	 * Log before.
	 *
	 * @param joinPoint the join point
	 */
	@Before(POINTCUT_EXPR)
	public void logBefore(JoinPoint joinPoint) {

		if (logger.isDebugEnabled()) {
			logger.debug(getSignature(joinPoint, true) + " - start");
		}
	}


	/**
	 * Log after.
	 *
	 * @param joinPoint the join point
	 * @param result the result
	 */
	@AfterReturning(pointcut = POINTCUT_EXPR, returning = "result")
	public void logAfter(JoinPoint joinPoint, Object result) {

		if (logger.isDebugEnabled()) {
			String returnValue = this.getValue(result);
			String signature = getSignature(joinPoint, true);
			if ((returnValue == null) && signature.endsWith("void")) {
				logger.debug(signature + " - end");
			} else {
				logger.debug(signature + " - end - Return : " + returnValue);
			}
		}
	}

	/**
	 * Log after throwing.
	 *
	 * @param joinPoint the join point
	 * @param exception the exception
	 */
	@AfterThrowing(pointcut = POINTCUT_EXPR, throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {

		logger.error(getSignature(joinPoint, true) + " - " + "ERROR");
		logger.error("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
		logger.error("Cause : " + exception.getCause());
	}

	/**
	 * Gets the signature.
	 *
	 * @param joinPoint the join point
	 * @param simpleClassName the simple class name
	 * @return the signature
	 */
	private String getSignature(JoinPoint joinPoint, boolean simpleClassName) {

		StringBuilder sb = new StringBuilder();

		String className = simpleClassName ? joinPoint.getTarget().getClass().getSimpleName() : joinPoint.getTarget().getClass().getName();
		sb.append(className.contains("$$")?className.substring(0, className.indexOf("$$")):className);
		sb.append("::").append(joinPoint.getSignature().getName());

		if (joinPoint.getSignature() instanceof MethodSignature) {
			sb.append("(");

			Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
			StringJoiner joiner = new StringJoiner(", ");
			for (Class<?> parameterType : method.getParameterTypes()) {
				joiner.add(simpleClassName ? parameterType.getSimpleName() : parameterType.getName());
			}
			sb.append(joiner.toString());
			sb.append(") -> ");
			sb.append(simpleClassName ? method.getReturnType().getSimpleName() : method.getReturnType().getName());
		} else {
			sb.append("()");
		}

		return sb.toString();
	}

	/**
	 * Gets the value.
	 *
	 * @param result the result
	 * @return the value
	 */
	private String getValue(Object result) {

		String returnValue = null;
		if (null != result) {
			if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
				returnValue = ReflectionToStringBuilder.toString(result);
			} else {
				returnValue = result.toString();
			}
		}
		return returnValue;
	}




}
