package com.example.educore.certificates.application.queries;

import com.example.educore.certificates.application.dto.IssuedCertificateResponse;
import com.example.educore.certificates.domain.model.IssuedCertificateRepository;
import com.example.educore.sharedkernel.application.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetIssuedCertificatesQueryHandler
        implements QueryHandler<GetIssuedCertificatesQuery, List<IssuedCertificateResponse>> {

    private final IssuedCertificateRepository certificateRepository;

    @Override
    public List<IssuedCertificateResponse> handle(GetIssuedCertificatesQuery query) {
        return certificateRepository.findAllByOrderByIssuedAtDescNumberDesc().stream()
                .map(c -> new IssuedCertificateResponse(
                        c.getId(), c.getType(), c.getStudentId(),
                        c.getNumber(), c.getYear(), c.getIssuedAt().toString()))
                .toList();
    }
}
