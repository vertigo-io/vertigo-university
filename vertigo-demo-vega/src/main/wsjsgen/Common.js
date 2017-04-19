/**
 * These routes are generated automatically.
 * Facade io.vertigo.pandora.webservices.common.CommonWebServices
 */

const ROOT = "/common";
import urlBuilder from 'focus-core/util/url/builder';


const urlSearch = urlBuilder('/common/search', 'POST')
export function search({/*String*/ criteria, /*integer*/ listState.top, /*integer,optional*/ listState.skip, /*String,optional*/ listState.sortFieldName, /*boolean,optional*/ listState.sortDesc}) {
  /* response FacetedQueryResult */  
  return urlSearch({bodyData:{criteria}, queryData: {listState.top, listState.skip, listState.sortFieldName, listState.sortDesc}});
}

const urlIndexAll = urlBuilder('/common/index', 'GET')
export function indexAll() {
  /* response long */  
  return urlIndexAll();
}

const urlReloadAll = urlBuilder('/common/reload', 'GET')
export function reloadAll() {
  /* response long */  
  return urlReloadAll();
}

const urlSearchMovies = urlBuilder('/common/search', 'GET')
export function searchMovies({/*String*/ q, /*integer*/ listState.top, /*integer,optional*/ listState.skip, /*String,optional*/ listState.sortFieldName, /*boolean,optional*/ listState.sortDesc}) {
  /* response FacetedQueryResult<? extends io.vertigo.dynamo.domain.model.DtObject, io.vertigo.dynamo.search.model.SearchQuery> */  
  return urlSearchMovies({queryData: {q, listState.top, listState.skip, listState.sortFieldName, listState.sortDesc}});
}

