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
            String query = "SELECT " +
                    "COUNT(DISTINCT b.id) as totalBuildings, " +
                    "COUNT(DISTINCT r.id) as totalRooms, " +
                    "SUM(CASE WHEN r.status = 'AVAILABLE' THEN 1 ELSE 0 END) as emptyRooms, " +
                    "SUM(CASE WHEN r.status = 'OCCUPIED' THEN 1 ELSE 0 END) as rentedRooms, " +
                    "COUNT(DISTINCT c.id) as totalCustomers, " +
                    "COUNT(DISTINCT ct.id) as activeContracts, " +
                    "COALESCE(SUM(CASE WHEN i.status = 'PAID' THEN i.total_amount ELSE 0 END), 0) as monthlyRevenue, " +
                    "SUM(CASE WHEN i.status != 'PAID' THEN 1 ELSE 0 END) as unpaidInvoices " +
                    "FROM buildings b " +
                    "RIGHT JOIN rooms r ON r.building_id = b.id " +
                    "LEFT JOIN contracts ct ON ct.room_id = r.id " +
                    "LEFT JOIN customers c ON c.id = ct.customer_id " +
                    "LEFT JOIN invoices i ON i.contract_id = ct.id";

            return (DashboardDto) entityManager.createNativeQuery(query, "DashboardMapping")
                    .getSingleResult();
        }

    }
