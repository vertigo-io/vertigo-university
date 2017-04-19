/**
 * These routes are generated automatically.
 * Facade io.vertigo.pandora.webservices.persons.PersonWebServices
 */

const ROOT = "/persons";
import urlBuilder from 'focus-core/util/url/builder';


const urlSearch = urlBuilder('/persons/search', 'POST')
export function search({/*String*/ criteria, /*UiSelectedFacets*/ facets, /*String,optional*/ group, /*integer*/ listState.top, /*integer,optional*/ listState.skip, /*String,optional*/ listState.sortFieldName, /*boolean,optional*/ listState.sortDesc}) {
  /* response FacetedQueryResult */  
  return urlSearch({bodyData:{criteria, facets, group}, queryData: {listState.top, listState.skip, listState.sortFieldName, listState.sortDesc}});
}

const urlSearchPersons = urlBuilder('/persons/search', 'GET')
export function searchPersons({/*String*/ q, /*String,optional*/ group, /*integer*/ listState.top, /*integer,optional*/ listState.skip, /*String,optional*/ listState.sortFieldName, /*boolean,optional*/ listState.sortDesc}) {
  /* response FacetedQueryResult<PersonIndex, io.vertigo.dynamo.search.model.SearchQuery> */  
  return urlSearchPersons({queryData: {q, group, listState.top, listState.skip, listState.sortFieldName, listState.sortDesc}});
}

const urlGetPerson = urlBuilder('/persons/${id}', 'GET')
export function getPerson({/*long*/ id}) {
  /* response Person */  
  return urlGetPerson({urlData:{id}});
}

const urlUpdatePerson = urlBuilder('/persons/${id}', 'PUT')
export function updatePerson({/*long*/ id, /*Person*/ person}) {
  /* response Person */  
  return urlUpdatePerson({bodyData:{...person}, urlData:{id}});
}

const urlCreatePerson = urlBuilder('/persons/', 'POST')
export function createPerson({/*Person*/ person}) {
  /* response void */  
  return urlCreatePerson({bodyData:{...person}});
}

