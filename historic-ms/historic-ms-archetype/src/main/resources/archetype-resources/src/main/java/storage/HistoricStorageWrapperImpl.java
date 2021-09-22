package $ import de.htw.saar.smartcity.aggregator.lib.properties.MinioApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.storage.HistoricStorageWrapper;
import org.springframework.stereotype.Component;

{package}.storage;

@Component
public class HistoricStorageWrapperImpl extends HistoricStorageWrapper {

    public HistoricStorageWrapperImpl(MinioApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
