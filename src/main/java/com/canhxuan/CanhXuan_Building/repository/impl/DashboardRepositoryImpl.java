    package com.canhxuan.CanhXuan_Building.repository.impl;

    import com.canhxuan.CanhXuan_Building.dto.response.DashboardDto;
    import com.canhxuan.CanhXuan_Building.repository.DashboardRepository;
    import jakarta.persistence.EntityManager;
    import jakarta.persistence.PersistenceContext;
    import org.springframework.stereotype.Repository;

    @Repository
    public class DashboardRepositoryImpl implements DashboardRepository {

        @PersistenceContext
        private EntityManager entityManager;

        @Override
        public DashboardDto getDashboard() {
            String query = """
        SELECT new com.canhxuan.CanhXuan_Building.dto.response.DashboardDto(
            COUNT(DISTINCT b.id),
            COUNT(DISTINCT r.id),
            SUM(CASE WHEN r.status = com.canhxuan.CanhXuan_Building.entity.RoomStatus.AVAILABLE THEN 1 ELSE 0 END),
            SUM(CASE WHEN r.status = com.canhxuan.CanhXuan_Building.entity.RoomStatus.OCCUPIED THEN 1 ELSE 0 END),
            COUNT(DISTINCT c.id),
            SUM(CASE WHEN ct.status = com.canhxuan.CanhXuan_Building.entity.ContractStatus.ACTIVE THEN 1 ELSE 0 END),
            CAST( COALESCE(SUM(CASE WHEN i.status = com.canhxuan.CanhXuan_Building.entity.InvoiceStatus.PAID
                AND MONTH(i.paidAt) = MONTH(CURRENT_DATE)
                AND YEAR(i.paidAt) = YEAR(CURRENT_DATE)
                THEN i.totalAmount ELSE 0.0 END), 0.0) AS double),
            SUM(CASE WHEN i.status = com.canhxuan.CanhXuan_Building.entity.InvoiceStatus.UNPAID THEN 1 ELSE 0 END)
        )
        FROM Building b
        LEFT JOIN b.roomList r
        LEFT JOIN Customer c on r.id = c.id
        LEFT JOIN Contract ct ON ct.room.id = r.id
        LEFT JOIN Invoice i ON i.contract.id = ct.id
        """;

            return entityManager.createQuery(query, DashboardDto.class)
                    .getSingleResult();
        }
    }
