package com.example.educore.certificates.infrastructure.graphql;

import com.example.educore.certificates.application.dto.IssuedCertificateResponse;
import com.example.educore.certificates.application.queries.GetIssuedCertificatesQuery;
import com.example.educore.sharedkernel.application.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("@perm.can('CERTIFICATES','READ')")
@RequiredArgsConstructor
public class CertificateQueryController {

    private final Mediator mediator;

    @QueryMapping
    public List<IssuedCertificateResponse> issuedCertificates() {
        return mediator.ask(new GetIssuedCertificatesQuery());
    }
}
