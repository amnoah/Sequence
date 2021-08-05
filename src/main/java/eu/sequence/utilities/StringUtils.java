package eu.sequence.utilities;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class StringUtils {

    public String stringListToString(List<String> stringList, boolean space, boolean newLine) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < stringList.size(); i++) {
            stringBuilder.append(stringList.get(i));

            if (stringList.size() - i != 1) {
                if (space) {
                    stringBuilder.append(" ");
                }

                if (newLine) {
                    stringBuilder.append("\n");
                }
            }
        }

        return stringBuilder.toString();
    }
}