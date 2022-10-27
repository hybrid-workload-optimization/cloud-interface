package kr.co.strato.cloud.aks.service;

import org.springframework.stereotype.Component;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;

import kr.co.strato.cloud.aks.exception.BusinessException;
import kr.co.strato.cloud.aks.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AzureCredential {

	public AzureResourceManager getAzureAuth(String clientId, String clientSecret, String tenantId, String subscriptionId) {
		
		final AzureProfile profile = new AzureProfile(AzureEnvironment.AZURE);
        final TokenCredential credential = new ClientSecretCredentialBuilder()
        		.clientId(clientId)
        		.clientSecret(clientSecret)
        		.tenantId(tenantId)
            .build();
        
		AzureResourceManager azureResourceManager;
		
		try {
            azureResourceManager = AzureResourceManager
                    .configure()
                    .withLogLevel(HttpLogDetailLevel.BASIC)
                    .authenticate(credential, profile)
                    .withSubscription(subscriptionId);
        } catch(Exception e) {
        	log.error("[getAzureAuth] azure auth fail", e);
			throw new BusinessException(ErrorCode.AZURE_UNAUTHORIZED);
        }
		
		return azureResourceManager;
	}
}
