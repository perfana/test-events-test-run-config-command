package io.perfana.events.testrunconfigcommand;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonConverterTest {

    private static final String SIMPLE_JSON = "{\n" +
            "    \"fruit\": \"Apple\",\n" +
            "    \"size\": \"Large\",\n" +
            "    \"color\": \"Red\"\n" +
            "}";

    private static final String K8S_JSON = "{\n" +
            "    \"apiVersion\": \"apps/v1\",\n" +
            "    \"kind\": \"Deployment\",\n" +
            "    \"metadata\": {\n" +
            "        \"annotations\": {\n" +
            "            \"deployment.kubernetes.io/revision\": \"3\",\n" +
            "            \"meta.helm.sh/release-name\": \"optimus-prime-fe\",\n" +
            "            \"meta.helm.sh/release-namespace\": \"acme\"\n" +
            "        },\n" +
            "        \"creationTimestamp\": \"2022-06-30T21:14:47Z\",\n" +
            "        \"generation\": 3,\n" +
            "        \"labels\": {\n" +
            "            \"app.kubernetes.io/instance\": \"optimus-prime-fe\",\n" +
            "            \"app.kubernetes.io/managed-by\": \"Helm\",\n" +
            "            \"app.kubernetes.io/name\": \"afterburner\",\n" +
            "            \"app.kubernetes.io/version\": \"2.1.7-jdk11\",\n" +
            "            \"helm.sh/chart\": \"afterburner-0.1.1\",\n" +
            "            \"helm.toolkit.fluxcd.io/name\": \"optimus-prime-fe\",\n" +
            "            \"helm.toolkit.fluxcd.io/namespace\": \"acme\"\n" +
            "        },\n" +
            "        \"name\": \"optimus-prime-fe-afterburner\",\n" +
            "        \"namespace\": \"acme\",\n" +
            "        \"resourceVersion\": \"1607932\",\n" +
            "        \"uid\": \"fbee3c61-4145-4176-82b2-2bfc5aaa6d46\"\n" +
            "    },\n" +
            "    \"spec\": {\n" +
            "        \"progressDeadlineSeconds\": 600,\n" +
            "        \"replicas\": 1,\n" +
            "        \"revisionHistoryLimit\": 10,\n" +
            "        \"selector\": {\n" +
            "            \"matchLabels\": {\n" +
            "                \"app.kubernetes.io/instance\": \"optimus-prime-fe\",\n" +
            "                \"app.kubernetes.io/name\": \"afterburner\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"strategy\": {\n" +
            "            \"rollingUpdate\": {\n" +
            "                \"maxSurge\": \"25%\",\n" +
            "                \"maxUnavailable\": \"25%\"\n" +
            "            },\n" +
            "            \"type\": \"RollingUpdate\"\n" +
            "        },\n" +
            "        \"template\": {\n" +
            "            \"metadata\": {\n" +
            "                \"creationTimestamp\": null,\n" +
            "                \"labels\": {\n" +
            "                    \"app.kubernetes.io/instance\": \"optimus-prime-fe\",\n" +
            "                    \"app.kubernetes.io/name\": \"afterburner\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"spec\": {\n" +
            "                \"containers\": [\n" +
            "                    {\n" +
            "                        \"env\": [\n" +
            "                            {\n" +
            "                                \"name\": \"JAVA_OPTS\",\n" +
            "                                \"value\": \"-Xmx312m\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"JDK_JAVA_OPTIONS\",\n" +
            "                                \"value\": \"-javaagent:/pyroscope.jar\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"PYROSCOPE_APPLICATION_NAME\",\n" +
            "                                \"value\": \"optimus-prime-fe\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"PYROSCOPE_AUTH_TOKEN\",\n" +
            "                                \"value\": \"s3cr3t\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"PYROSCOPE_LOG_LEVEL\",\n" +
            "                                \"value\": \"info\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"PYROSCOPE_PROFILER_EVENT\",\n" +
            "                                \"value\": \"itimer\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"PYROSCOPE_PROFILING_INTERVAL\",\n" +
            "                                \"value\": \"13ms\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"PYROSCOPE_SERVER_ADDRESS\",\n" +
            "                                \"value\": \"https://pyroscope-ingest.demo.perfana.cloud\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"PYROSCOPE_UPLOAD_INTERVAL\",\n" +
            "                                \"value\": \"3s\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"afterburner.datasource.employee.driver-class-name\",\n" +
            "                                \"value\": \"com.mysql.cj.jdbc.Driver\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"afterburner.datasource.employee.jdbc-url\",\n" +
            "                                \"value\": \"jdbc:mysql://afterburner-db-mysql-headless:3306/employees\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"afterburner.datasource.employee.password\",\n" +
            "                                \"value\": \"s3cr3t\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"afterburner.datasource.employee.username\",\n" +
            "                                \"value\": \"afterburner\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"afterburner.name\",\n" +
            "                                \"value\": \"optimus-prime-fe\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"afterburner.remote.call.base_url\",\n" +
            "                                \"value\": \"http://optimus-prime-be-afterburner:8080\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"afterburner.remote.call.httpclient.socket.timeout.millis\",\n" +
            "                                \"value\": \"10000\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"logging.level.com.zaxxer.hikari\",\n" +
            "                                \"value\": \"TRACE\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"logging.level.com.zaxxer.hikari.HikariConfig\",\n" +
            "                                \"value\": \"DEBUG\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"management.metrics.tags.service\",\n" +
            "                                \"value\": \"optimus-prime-fe\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"management.metrics.tags.system_under_test\",\n" +
            "                                \"value\": \"OptimusPrime\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"management.metrics.tags.test_environment\",\n" +
            "                                \"value\": \"acme\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"spring.application.name\",\n" +
            "                                \"value\": \"optimus-prime-fe\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"spring.profiles.active\",\n" +
            "                                \"value\": \"employee-db\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"spring.sleuth.baggage-keys\",\n" +
            "                                \"value\": \"perfana-test-run-id,perfana-request-name\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"spring.sleuth.keys.http.headers\",\n" +
            "                                \"value\": \"perfana-test-run-id,perfana-request-name\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"spring.sleuth.propagation-keys\",\n" +
            "                                \"value\": \"perfana-test-run-id,perfana-request-name\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"spring.sleuth.propagation.tag.enabled\",\n" +
            "                                \"value\": \"true\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"spring.sleuth.propagation.tag.whitelisted-keys\",\n" +
            "                                \"value\": \"perfana-test-run-id,perfana-request-name\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"spring.sleuth.sampler.probability\",\n" +
            "                                \"value\": \"0.1\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"spring.zipkin.base-url\",\n" +
            "                                \"value\": \"https://jaeger-collector.demo.perfana.cloud/\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"spring.zipkin.enabled\",\n" +
            "                                \"value\": \"true\"\n" +
            "                            }\n" +
            "                        ],\n" +
            "                        \"image\": \"stokpop/afterburner-jdk:2.1.8-jdk11\",\n" +
            "                        \"imagePullPolicy\": \"IfNotPresent\",\n" +
            "                        \"name\": \"afterburner\",\n" +
            "                        \"ports\": [\n" +
            "                            {\n" +
            "                                \"containerPort\": 8080,\n" +
            "                                \"name\": \"http\",\n" +
            "                                \"protocol\": \"TCP\"\n" +
            "                            }\n" +
            "                        ],\n" +
            "                        \"resources\": {\n" +
            "                            \"limits\": {\n" +
            "                                \"cpu\": \"1\",\n" +
            "                                \"memory\": \"1Gi\"\n" +
            "                            },\n" +
            "                            \"requests\": {\n" +
            "                                \"cpu\": \"500m\",\n" +
            "                                \"memory\": \"1Gi\"\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"securityContext\": {},\n" +
            "                        \"terminationMessagePath\": \"/dev/termination-log\",\n" +
            "                        \"terminationMessagePolicy\": \"File\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"dnsPolicy\": \"ClusterFirst\",\n" +
            "                \"restartPolicy\": \"Always\",\n" +
            "                \"schedulerName\": \"default-scheduler\",\n" +
            "                \"securityContext\": {},\n" +
            "                \"serviceAccount\": \"optimus-prime-fe-afterburner\",\n" +
            "                \"serviceAccountName\": \"optimus-prime-fe-afterburner\",\n" +
            "                \"terminationGracePeriodSeconds\": 30\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "    \"status\": {\n" +
            "        \"availableReplicas\": 1,\n" +
            "        \"conditions\": [\n" +
            "            {\n" +
            "                \"lastTransitionTime\": \"2022-07-05T11:39:19Z\",\n" +
            "                \"lastUpdateTime\": \"2022-07-05T11:39:19Z\",\n" +
            "                \"message\": \"Deployment has minimum availability.\",\n" +
            "                \"reason\": \"MinimumReplicasAvailable\",\n" +
            "                \"status\": \"True\",\n" +
            "                \"type\": \"Available\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"lastTransitionTime\": \"2022-06-30T21:14:47Z\",\n" +
            "                \"lastUpdateTime\": \"2022-07-05T11:39:19Z\",\n" +
            "                \"message\": \"ReplicaSet \\\"optimus-prime-fe-afterburner-78dd6d9c8f\\\" has successfully progressed.\",\n" +
            "                \"reason\": \"NewReplicaSetAvailable\",\n" +
            "                \"status\": \"True\",\n" +
            "                \"type\": \"Progressing\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"observedGeneration\": 3,\n" +
            "        \"readyReplicas\": 1,\n" +
            "        \"replicas\": 1,\n" +
            "        \"updatedReplicas\": 1\n" +
            "    }\n" +
            "}";

    private static final String MY_SQL_JSON = "{\n" +
            "    \"apiVersion\": \"apps/v1\",\n" +
            "    \"kind\": \"StatefulSet\",\n" +
            "    \"metadata\": {\n" +
            "        \"annotations\": {\n" +
            "            \"meta.helm.sh/release-name\": \"afterburner-db\",\n" +
            "            \"meta.helm.sh/release-namespace\": \"acme\"\n" +
            "        },\n" +
            "        \"creationTimestamp\": \"2022-07-06T12:27:53Z\",\n" +
            "        \"generation\": 15,\n" +
            "        \"labels\": {\n" +
            "            \"app.kubernetes.io/component\": \"primary\",\n" +
            "            \"app.kubernetes.io/instance\": \"afterburner-db\",\n" +
            "            \"app.kubernetes.io/managed-by\": \"Helm\",\n" +
            "            \"app.kubernetes.io/name\": \"mysql\",\n" +
            "            \"helm.sh/chart\": \"mysql-9.2.6\",\n" +
            "            \"helm.toolkit.fluxcd.io/name\": \"afterburner-db\",\n" +
            "            \"helm.toolkit.fluxcd.io/namespace\": \"acme\"\n" +
            "        },\n" +
            "        \"name\": \"afterburner-db-mysql\",\n" +
            "        \"namespace\": \"acme\",\n" +
            "        \"resourceVersion\": \"13972072\",\n" +
            "        \"uid\": \"9b683c74-8d0f-4ae0-b890-e5bc86e3c40b\"\n" +
            "    },\n" +
            "    \"spec\": {\n" +
            "        \"podManagementPolicy\": \"OrderedReady\",\n" +
            "        \"replicas\": 1,\n" +
            "        \"revisionHistoryLimit\": 10,\n" +
            "        \"selector\": {\n" +
            "            \"matchLabels\": {\n" +
            "                \"app.kubernetes.io/component\": \"primary\",\n" +
            "                \"app.kubernetes.io/instance\": \"afterburner-db\",\n" +
            "                \"app.kubernetes.io/name\": \"mysql\"\n" +
            "            }\n" +
            "        },\n" +
            "        \"serviceName\": \"afterburner-db-mysql\",\n" +
            "        \"template\": {\n" +
            "            \"metadata\": {\n" +
            "                \"annotations\": {\n" +
            "                    \"checksum/configuration\": \"bdd6432051bcb4618e7f43cbdf32cb616181e6e79b09364d37becbaa777c8582\"\n" +
            "                },\n" +
            "                \"creationTimestamp\": null,\n" +
            "                \"labels\": {\n" +
            "                    \"app.kubernetes.io/component\": \"primary\",\n" +
            "                    \"app.kubernetes.io/instance\": \"afterburner-db\",\n" +
            "                    \"app.kubernetes.io/managed-by\": \"Helm\",\n" +
            "                    \"app.kubernetes.io/name\": \"mysql\",\n" +
            "                    \"helm.sh/chart\": \"mysql-9.2.6\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"spec\": {\n" +
            "                \"affinity\": {\n" +
            "                    \"podAntiAffinity\": {\n" +
            "                        \"preferredDuringSchedulingIgnoredDuringExecution\": [\n" +
            "                            {\n" +
            "                                \"podAffinityTerm\": {\n" +
            "                                    \"labelSelector\": {\n" +
            "                                        \"matchLabels\": {\n" +
            "                                            \"app.kubernetes.io/instance\": \"afterburner-db\",\n" +
            "                                            \"app.kubernetes.io/name\": \"mysql\"\n" +
            "                                        }\n" +
            "                                    },\n" +
            "                                    \"namespaces\": [\n" +
            "                                        \"acme\"\n" +
            "                                    ],\n" +
            "                                    \"topologyKey\": \"kubernetes.io/hostname\"\n" +
            "                                },\n" +
            "                                \"weight\": 1\n" +
            "                            }\n" +
            "                        ]\n" +
            "                    }\n" +
            "                },\n" +
            "                \"containers\": [\n" +
            "                    {\n" +
            "                        \"env\": [\n" +
            "                            {\n" +
            "                                \"name\": \"BITNAMI_DEBUG\",\n" +
            "                                \"value\": \"false\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"MYSQL_ROOT_PASSWORD\",\n" +
            "                                \"valueFrom\": {\n" +
            "                                    \"secretKeyRef\": {\n" +
            "                                        \"key\": \"mysql-root-password\",\n" +
            "                                        \"name\": \"mysql-credentials\"\n" +
            "                                    }\n" +
            "                                }\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"MYSQL_USER\",\n" +
            "                                \"value\": \"afterburner\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"MYSQL_PASSWORD\",\n" +
            "                                \"valueFrom\": {\n" +
            "                                    \"secretKeyRef\": {\n" +
            "                                        \"key\": \"mysql-password\",\n" +
            "                                        \"name\": \"mysql-credentials\"\n" +
            "                                    }\n" +
            "                                }\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"MYSQL_DATABASE\",\n" +
            "                                \"value\": \"employees\"\n" +
            "                            }\n" +
            "                        ],\n" +
            "                        \"image\": \"docker.io/bitnami/mysql:8.0.30-debian-11-r6\",\n" +
            "                        \"imagePullPolicy\": \"IfNotPresent\",\n" +
            "                        \"livenessProbe\": {\n" +
            "                            \"exec\": {\n" +
            "                                \"command\": [\n" +
            "                                    \"/bin/bash\",\n" +
            "                                    \"-ec\",\n" +
            "                                    \"password_aux=\\\"${MYSQL_ROOT_PASSWORD:-}\\\"\\nif [[ -f \\\"${MYSQL_ROOT_PASSWORD_FILE:-}\\\" ]]; then\\n    password_aux=$(cat \\\"$MYSQL_ROOT_PASSWORD_FILE\\\")\\nfi\\nmysqladmin status -uroot -p\\\"${password_aux}\\\"\\n\"\n" +
            "                                ]\n" +
            "                            },\n" +
            "                            \"failureThreshold\": 3,\n" +
            "                            \"initialDelaySeconds\": 5,\n" +
            "                            \"periodSeconds\": 10,\n" +
            "                            \"successThreshold\": 1,\n" +
            "                            \"timeoutSeconds\": 1\n" +
            "                        },\n" +
            "                        \"name\": \"mysql\",\n" +
            "                        \"ports\": [\n" +
            "                            {\n" +
            "                                \"containerPort\": 3306,\n" +
            "                                \"name\": \"mysql\",\n" +
            "                                \"protocol\": \"TCP\"\n" +
            "                            }\n" +
            "                        ],\n" +
            "                        \"readinessProbe\": {\n" +
            "                            \"exec\": {\n" +
            "                                \"command\": [\n" +
            "                                    \"/bin/bash\",\n" +
            "                                    \"-ec\",\n" +
            "                                    \"password_aux=\\\"${MYSQL_ROOT_PASSWORD:-}\\\"\\nif [[ -f \\\"${MYSQL_ROOT_PASSWORD_FILE:-}\\\" ]]; then\\n    password_aux=$(cat \\\"$MYSQL_ROOT_PASSWORD_FILE\\\")\\nfi\\nmysqladmin status -uroot -p\\\"${password_aux}\\\"\\n\"\n" +
            "                                ]\n" +
            "                            },\n" +
            "                            \"failureThreshold\": 3,\n" +
            "                            \"initialDelaySeconds\": 5,\n" +
            "                            \"periodSeconds\": 10,\n" +
            "                            \"successThreshold\": 1,\n" +
            "                            \"timeoutSeconds\": 1\n" +
            "                        },\n" +
            "                        \"resources\": {\n" +
            "                            \"limits\": {\n" +
            "                                \"cpu\": \"1500m\",\n" +
            "                                \"memory\": \"512Mi\"\n" +
            "                            },\n" +
            "                            \"requests\": {\n" +
            "                                \"cpu\": \"100m\",\n" +
            "                                \"memory\": \"512Mi\"\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"securityContext\": {\n" +
            "                            \"runAsNonRoot\": true,\n" +
            "                            \"runAsUser\": 1001\n" +
            "                        },\n" +
            "                        \"startupProbe\": {\n" +
            "                            \"exec\": {\n" +
            "                                \"command\": [\n" +
            "                                    \"/bin/bash\",\n" +
            "                                    \"-ec\",\n" +
            "                                    \"password_aux=\\\"${MYSQL_ROOT_PASSWORD:-}\\\"\\nif [[ -f \\\"${MYSQL_ROOT_PASSWORD_FILE:-}\\\" ]]; then\\n    password_aux=$(cat \\\"$MYSQL_ROOT_PASSWORD_FILE\\\")\\nfi\\nmysqladmin status -uroot -p\\\"${password_aux}\\\"\\n\"\n" +
            "                                ]\n" +
            "                            },\n" +
            "                            \"failureThreshold\": 10,\n" +
            "                            \"initialDelaySeconds\": 15,\n" +
            "                            \"periodSeconds\": 10,\n" +
            "                            \"successThreshold\": 1,\n" +
            "                            \"timeoutSeconds\": 1\n" +
            "                        },\n" +
            "                        \"terminationMessagePath\": \"/dev/termination-log\",\n" +
            "                        \"terminationMessagePolicy\": \"File\",\n" +
            "                        \"volumeMounts\": [\n" +
            "                            {\n" +
            "                                \"mountPath\": \"/bitnami/mysql\",\n" +
            "                                \"name\": \"data\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"mountPath\": \"/opt/bitnami/mysql/conf/my.cnf\",\n" +
            "                                \"name\": \"config\",\n" +
            "                                \"subPath\": \"my.cnf\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"mountPath\": \"/tmp\",\n" +
            "                                \"name\": \"employee-db-mount\"\n" +
            "                            }\n" +
            "                        ]\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"dnsPolicy\": \"ClusterFirst\",\n" +
            "                \"initContainers\": [\n" +
            "                    {\n" +
            "                        \"args\": [\n" +
            "                            \"apk --update add curl; cd /tmp; EMP_DB_URL=\\\"https://github.com/perfana/perfana-demo/raw/master/test_db/employees-original.sql\\\"; EMP_DB=$(basename $EMP_DB_URL); echo \\\"download $EMP_DB_URL\\\"; curl -Ss -O -L $EMP_DB_URL;\"\n" +
            "                        ],\n" +
            "                        \"command\": [\n" +
            "                            \"/bin/sh\",\n" +
            "                            \"-c\"\n" +
            "                        ],\n" +
            "                        \"image\": \"alpine:3.12\",\n" +
            "                        \"imagePullPolicy\": \"IfNotPresent\",\n" +
            "                        \"name\": \"load-db-hack\",\n" +
            "                        \"resources\": {},\n" +
            "                        \"terminationMessagePath\": \"/dev/termination-log\",\n" +
            "                        \"terminationMessagePolicy\": \"File\",\n" +
            "                        \"volumeMounts\": [\n" +
            "                            {\n" +
            "                                \"mountPath\": \"/tmp\",\n" +
            "                                \"name\": \"employee-db-mount\"\n" +
            "                            }\n" +
            "                        ]\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"restartPolicy\": \"Always\",\n" +
            "                \"schedulerName\": \"default-scheduler\",\n" +
            "                \"securityContext\": {\n" +
            "                    \"fsGroup\": 1001\n" +
            "                },\n" +
            "                \"serviceAccount\": \"afterburner-db-mysql\",\n" +
            "                \"serviceAccountName\": \"afterburner-db-mysql\",\n" +
            "                \"terminationGracePeriodSeconds\": 30,\n" +
            "                \"volumes\": [\n" +
            "                    {\n" +
            "                        \"configMap\": {\n" +
            "                            \"defaultMode\": 420,\n" +
            "                            \"name\": \"afterburner-db-mysql\"\n" +
            "                        },\n" +
            "                        \"name\": \"config\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"employee-db-mount\",\n" +
            "                        \"persistentVolumeClaim\": {\n" +
            "                            \"claimName\": \"employee-db-volume-pvc\"\n" +
            "                        }\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        \"updateStrategy\": {\n" +
            "            \"type\": \"RollingUpdate\"\n" +
            "        },\n" +
            "        \"volumeClaimTemplates\": [\n" +
            "            {\n" +
            "                \"apiVersion\": \"v1\",\n" +
            "                \"kind\": \"PersistentVolumeClaim\",\n" +
            "                \"metadata\": {\n" +
            "                    \"creationTimestamp\": null,\n" +
            "                    \"labels\": {\n" +
            "                        \"app.kubernetes.io/component\": \"primary\",\n" +
            "                        \"app.kubernetes.io/instance\": \"afterburner-db\",\n" +
            "                        \"app.kubernetes.io/name\": \"mysql\"\n" +
            "                    },\n" +
            "                    \"name\": \"data\"\n" +
            "                },\n" +
            "                \"spec\": {\n" +
            "                    \"accessModes\": [\n" +
            "                        \"ReadWriteOnce\"\n" +
            "                    ],\n" +
            "                    \"resources\": {\n" +
            "                        \"requests\": {\n" +
            "                            \"storage\": \"8Gi\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"volumeMode\": \"Filesystem\"\n" +
            "                },\n" +
            "                \"status\": {\n" +
            "                    \"phase\": \"Pending\"\n" +
            "                }\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    \"status\": {\n" +
            "        \"availableReplicas\": 1,\n" +
            "        \"collisionCount\": 0,\n" +
            "        \"currentReplicas\": 1,\n" +
            "        \"currentRevision\": \"afterburner-db-mysql-5bc44b6857\",\n" +
            "        \"observedGeneration\": 15,\n" +
            "        \"readyReplicas\": 1,\n" +
            "        \"replicas\": 1,\n" +
            "        \"updateRevision\": \"afterburner-db-mysql-5bc44b6857\",\n" +
            "        \"updatedReplicas\": 1\n" +
            "    }\n" +
            "}";

    @Test
    void testFlattenJson() {
        Map<String, String> jsonMap = JsonConverter.flatten(SIMPLE_JSON);

        assertEquals("Large", jsonMap.get("size"));
        assertEquals(3, jsonMap.size());
    }

    @Test
    void testFlattenJsonK8s() {
        Map<String, String> jsonMap = JsonConverter.flatten(K8S_JSON);

        assertEquals("25%", jsonMap.get("spec.strategy.rollingUpdate.maxSurge"));
        assertEquals("312m", jsonMap.get("spec.template.spec.containers.0.env.0.JAVA_OPTS.jvmArg.Xmx"));
        assertEquals(95, jsonMap.size());
    }

    @Test
    void testFlattenJsonK8sWithFilters() {
        String includes = "env,resources,image,replicas,strategy,kubernetes";
        String excludes = "status,password,TOKEN";

        Map<String, String> jsonMap = JsonConverter.flatten(K8S_JSON, includes, excludes);

        assertNull(jsonMap.get("status.updatedReplicas"));
        assertFalse(jsonMap.containsValue("s3cr3t"), "should not contain a s3cr3t");
        assertNull(jsonMap.get("spec.template.spec.containers.0.env.3.PYROSCOPE_AUTH_TOKEN"));
        assertNull(jsonMap.get("spec.template.spec.containers.0.env.11.afterburner.datasource.employee.password"));
        assertEquals(48, jsonMap.size());
    }

    @Test
    void testFlattenJsonK8sWithFiltersNoIncludes() {
        String includes = "";
        String excludes = "";

        Map<String, String> jsonMap = JsonConverter.flatten(K8S_JSON, includes, excludes);

        assertFalse(jsonMap.containsValue("s3cr3t"), "should not contain a s3cr3t");
        assertEquals(0, jsonMap.size());
    }

    @Test
    void testFlattenJsonMySql() {
        String includes = "env,resources,image,replicas,strategy,kubernetes";
        String excludes = "status,password,secret";

        Map<String, String> jsonMap = JsonConverter.flatten(MY_SQL_JSON, includes, excludes);
        assertEquals(31, jsonMap.size());
    }

}