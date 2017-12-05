import * as React from 'react'


export const Result = ({score, page}) => (
	<div className="result-container">
		<p>score: {score}</p>
		<a href={`http://wikipedia.com/${page}`}>{page}</a>
	</div>
)