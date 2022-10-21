package kr.co.strato.cloud.aks.plugin.model;

import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import kr.co.strato.cloud.eks.model.CloudAuthArg;
import lombok.Data;

@Data
public class MessageData {

	@Positive(message = "workJobIdx 는 null 또는 0을 허용하지 않으며, 양수만 허용합니다.")
	private Long workJobIdx;
	@NotEmpty(message = "callbackUrl 은 null 또는 공백을 허용하지 않습니다.")
	private String callbackUrl;
	
	@NotEmpty(message = "jobType 은 null 또는 공백을 허용하지 않습니다. (provisioning/scale/modify/delete)")
	private String jobType;
	@NotNull(message = "param 은 null 허용하지 않습니다.")
	private Object param;
	
}
