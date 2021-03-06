package br.ufc.great.syssu.servicemanagement.services;

import java.util.List;

import br.ufc.great.syssu.base.Pattern;
import br.ufc.great.syssu.base.Scope;
import br.ufc.great.syssu.base.Tuple;
import br.ufc.great.syssu.base.TupleSpaceException;
import br.ufc.great.syssu.base.TupleSpaceSecurityException;
import br.ufc.great.syssu.base.interfaces.IDomain;

public class ReadService extends AbstractQueryService {

    @Override
    public String getName() {
        return "read";
    }

    @Override
   protected List<Tuple> query(IDomain domain, Pattern pattern, String restriction, String key, long timeout, Scope scope) 
	   throws TupleSpaceException, TupleSpaceSecurityException {
    	return domain.read(pattern, restriction, key, scope);
    }
}
