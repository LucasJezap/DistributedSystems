package query;

import akka.actor.typed.ActorRef;
import message.MonitorQueryResponse;
import message.MonitorRequest;
import message.MonitorResponse;
import message.SatelliteMessage;

import java.util.HashMap;

public class QueryStorage {
    private final HashMap<ActorRef<SatelliteMessage>, HashMap<Integer, Query>> queries;

    public QueryStorage() {
        this.queries = new HashMap<>();
    }

    public void addStationQuery(MonitorRequest request) {
        if (!this.queries.containsKey(request.station)) {
            this.queries.put(request.station, new HashMap<>());
        }
        this.queries.get(request.station).put(request.queryID, new Query(request.range, request.firstSatelliteID));
    }

    public void updateQuery(MonitorQueryResponse response) {
        Query query = this.queries.get(response.station).get(response.queryID);
        if (query.isResponseRecorded(response.satelliteID)) {
            return;
        }
        query.addResponse(response.satelliteID, response.replyCode);
    }

    public boolean isQueryCompleted(MonitorQueryResponse response) {
        return this.queries.get(response.station).get(response.queryID).isQueryCompleted();
    }

    public boolean isQueryActive(MonitorQueryResponse response) {
        if (!this.queries.containsKey(response.station)) {
            return false;
        }
        return this.queries.get(response.station).containsKey(response.queryID);
    }

    public MonitorResponse getMonitorResponse(MonitorQueryResponse response) {
        HashMap<Integer, Query> stationQueries = this.queries.get(response.station);
        Query query = stationQueries.get(response.queryID);
        stationQueries.remove(response.queryID);
        if (stationQueries.size() == 0) {
            this.queries.remove(response.station);
        }

        return new MonitorResponse(
                response.queryID,
                query.getSatelliteResponsesBeforeTimeout(),
                query.getErrors()
        );
    }
}
