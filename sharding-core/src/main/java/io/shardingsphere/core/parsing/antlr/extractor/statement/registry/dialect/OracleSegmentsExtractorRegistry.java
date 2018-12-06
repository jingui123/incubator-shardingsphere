/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.core.parsing.antlr.extractor.statement.registry.dialect;

import io.shardingsphere.core.parsing.antlr.parser.SQLStatementType;
import io.shardingsphere.core.parsing.antlr.extractor.statement.SQLSegmentsExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.statement.impl.ddl.CreateIndexExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.statement.impl.ddl.CreateTableExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.statement.impl.ddl.dialect.oracle.OracleAlterIndexExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.statement.impl.ddl.dialect.oracle.OracleAlterTableExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.statement.impl.ddl.dialect.oracle.OracleDropIndexExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.statement.impl.ddl.dialect.oracle.OracleDropTableExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.statement.impl.ddl.dialect.oracle.OracleTruncateTableExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.statement.impl.tcl.TCLSegmentsExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.statement.registry.SQLSegmentsExtractorRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * SQL segments extractor registry for Oracle.
 * 
 * @author duhongjun
 * @author zhangliang
 */
public final class OracleSegmentsExtractorRegistry implements SQLSegmentsExtractorRegistry {
    
    private static final Map<SQLStatementType, SQLSegmentsExtractor> EXTRACTORS = new HashMap<>();
    
    static {
        registerDDL();
        registerTCL();
    }
    
    private static void registerDDL() {
        EXTRACTORS.put(SQLStatementType.CREATE_TABLE, new CreateTableExtractor());
        EXTRACTORS.put(SQLStatementType.ALTER_TABLE, new OracleAlterTableExtractor());
        EXTRACTORS.put(SQLStatementType.DROP_TABLE, new OracleDropTableExtractor());
        EXTRACTORS.put(SQLStatementType.TRUNCATE_TABLE, new OracleTruncateTableExtractor());
        EXTRACTORS.put(SQLStatementType.CREATE_INDEX, new CreateIndexExtractor());
        EXTRACTORS.put(SQLStatementType.ALTER_INDEX, new OracleAlterIndexExtractor());
        EXTRACTORS.put(SQLStatementType.DROP_INDEX, new OracleDropIndexExtractor());
    }
    
    private static void registerTCL() {
        EXTRACTORS.put(SQLStatementType.SET_TRANSACTION, new TCLSegmentsExtractor());
        EXTRACTORS.put(SQLStatementType.COMMIT, new TCLSegmentsExtractor());
        EXTRACTORS.put(SQLStatementType.ROLLBACK, new TCLSegmentsExtractor());
        EXTRACTORS.put(SQLStatementType.SAVEPOINT, new TCLSegmentsExtractor());
        EXTRACTORS.put(SQLStatementType.BEGIN_WORK, new TCLSegmentsExtractor());
    }
    
    @Override
    public SQLSegmentsExtractor getExtractor(final SQLStatementType type) {
        return EXTRACTORS.get(type);
    }
}